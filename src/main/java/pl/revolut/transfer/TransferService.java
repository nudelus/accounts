package pl.revolut.transfer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.revolut.account.Account;
import pl.revolut.account.AccountNotFoundException;
import pl.revolut.account.AccountRepository;
import pl.revolut.transaction.Transaction;
import pl.revolut.transaction.TransactionRepository;
import pl.revolut.transaction.TransactionType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The Transaction service object to handel transfer related business logic
 */
public class TransferService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransferService.class);

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    public TransferService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public void makeTransfer(Transfer transfer) {
        String sourceAccountNumber = transfer.getSourceAccountNumber();
        String targetAccountNumber = transfer.getTargetAccountNumber();

        LOGGER.debug("Transfer from source account {} to target account {} started", sourceAccountNumber, targetAccountNumber);

        Optional<Account> sourceAccount = accountRepository.retrieve(sourceAccountNumber);
        sourceAccount.orElseThrow(() -> new AccountNotFoundException(sourceAccountNumber));

        Optional<Account> targetAccount = accountRepository.retrieve(targetAccountNumber);
        targetAccount.orElseThrow(() -> new AccountNotFoundException(targetAccountNumber));

        List<String> transactionIds = new ArrayList<>();

        Double originalSourceBalance = sourceAccount.get().getBalance();
        Double originalTargetBalance = targetAccount.get().getBalance();

        try {
            String withdrawTransactionId = withdrawAmount(transfer, sourceAccount.get());
            transactionIds.add(withdrawTransactionId);

            String addTransactionId = addAmount(transfer, targetAccount.get());
            transactionIds.add(addTransactionId);

        } catch (Exception e) {
            LOGGER.error("Error occurred during transfer", e);
            rollBackTransfer(sourceAccount.get(), originalSourceBalance, targetAccount.get(), originalTargetBalance, transactionIds);
            throw new TransferException();
        }
        LOGGER.debug("Transfer from source account {} to target account {} ended", sourceAccountNumber, targetAccountNumber);
    }

    private String withdrawAmount(Transfer transfer, Account sourceAccount) {
        Double amountToWithDraw = transfer.getAmount();

        if (!transfer.getCurrency().equals(sourceAccount.getCurrency())) {
            amountToWithDraw = exchange(transfer.getCurrency(), sourceAccount.getCurrency(), amountToWithDraw);
        }

        withDrawFromSourceAccount(sourceAccount, amountToWithDraw);
        Transaction withDrawTransaction = createTransaction(TransactionType.SPENDING, transfer, amountToWithDraw);
        return transactionRepository.saveTransaction((withDrawTransaction));
    }


    private String addAmount(Transfer transfer, Account targetAccount) {
        Double amountToAdd = transfer.getAmount();

        if (!transfer.getCurrency().equals(targetAccount.getCurrency())) {
            amountToAdd = exchange(transfer.getCurrency(), targetAccount.getCurrency(), amountToAdd);
        }
        addToTargetAccount(targetAccount, amountToAdd);
        Transaction addTransaction = createTransaction(TransactionType.INCOME, transfer, amountToAdd);
        return transactionRepository.saveTransaction(addTransaction);
    }

    private void rollBackTransfer(Account sourceAccount, Double sourceBalance, Account targetAccount, Double targetBalance, List<String> transactionIds) {
        sourceAccount.setBalance(sourceBalance);
        accountRepository.modify(sourceAccount.getAccountNumber(), sourceAccount);
        targetAccount.setBalance(targetBalance);
        accountRepository.modify(targetAccount.getAccountNumber(), targetAccount);
        transactionIds.forEach(t -> transactionRepository.removeTransaction(t));
    }

    private void addToTargetAccount(Account account, Double amount) {
        account.setBalance(account.getBalance() + amount);
        accountRepository.modify(account.getAccountNumber(), account);
    }

    private void withDrawFromSourceAccount(Account account, Double amount) {
        Double accountBalance = account.getBalance();

        Double newBalance = accountBalance - amount;

        if (newBalance < 0) {
            throw new InsufficientFundsException(account.getAccountNumber());
        }

        account.setBalance(newBalance);
        accountRepository.modify(account.getAccountNumber(), account);

    }

    private Double exchange(String sourceCurrency, String targetCurrency, Double amount) {
        if (!sourceCurrency.equalsIgnoreCase(targetCurrency)) {
            Double exchangeRate = ExchangeRateApi.getExchangeRate(sourceCurrency, targetCurrency);
            return amount * exchangeRate;
        }

        return amount;
    }

    private Transaction createTransaction(TransactionType transactionType, Transfer transfer, Double exchangedAmount) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionType);
        if (TransactionType.INCOME.equals(transactionType)) {
            transaction.setAccountNumber(transfer.getTargetAccountNumber());
            transaction.setPartnerAccountNumber(transfer.getSourceAccountNumber());
        } else {
            transaction.setAccountNumber(transfer.getSourceAccountNumber());
            transaction.setPartnerAccountNumber(transfer.getTargetAccountNumber());
        }
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setAmountInAccountCurrency(exchangedAmount);
        transaction.setAmountInOriginalCurrency(transfer.getAmount());
        transaction.setCurrency(transfer.getCurrency());

        if (exchangedAmount != transfer.getAmount()) {
            transaction.setExchangeRate(exchangedAmount / transfer.getAmount());
        }

        return transaction;
    }

}
