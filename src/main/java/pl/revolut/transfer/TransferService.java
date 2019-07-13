package pl.revolut.transfer;

import pl.revolut.account.Account;
import pl.revolut.account.AccountRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public class TransferService {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    public TransferService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public void makeTransfer(Transfer transfer) {
        String sourceAccountNumber = transfer.getSourceAccountNumber();
        String targetAccountNumber = transfer.getTargetAccountNumber();

        Optional<Account> sourceAccount = accountRepository.retrieve(sourceAccountNumber);
        Optional<Account> targetAccount = accountRepository.retrieve(targetAccountNumber);

        transactionRepository.saveTransaction(createTransaction(TransactionType.SPENDING,transfer));
        transactionRepository.saveTransaction(createTransaction(TransactionType.INCOME,transfer));


    }

    private Transaction createTransaction(TransactionType transactionType,Transfer transfer) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionType);
        if(TransactionType.INCOME.equals(transactionType)) {
            transaction.setAccountNumber(transfer.getTargetAccountNumber());
            transaction.setPartnerAccountNumber(transfer.getSourceAccountNumber());
        } else {
            transaction.setAccountNumber(transfer.getSourceAccountNumber());
            transaction.setPartnerAccountNumber(transfer.getTargetAccountNumber());
        }
        transaction.setTransactionDate(LocalDateTime.now());

        return transaction;
    }

}
