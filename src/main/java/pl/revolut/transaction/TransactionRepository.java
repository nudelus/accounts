package pl.revolut.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The Transaction repository, simple map based in memory data store for transactions
 */
public class TransactionRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionRepository.class);

    private Map<String, List<Transaction>> transactionTable = new ConcurrentHashMap<>();


    public String saveTransaction(Transaction transaction) {
        String accountNumber = transaction.getAccountNumber();
        if (!transactionTable.containsKey(accountNumber)) {
            transactionTable.put(accountNumber,new ArrayList<>());
        }

        String transactionId = createTransactionId();

        transaction.setTransactionId(transactionId);
        transactionTable.get(accountNumber).add(transaction);
        LOGGER.debug("Transaction saved {}",transaction);
        return transactionId;
    }

    public void removeTransaction(String transactionId) {
        LOGGER.debug("Transaction saved with id {}",transactionId);
        transactionTable.entrySet().forEach(e -> e.getValue().removeIf(t -> t.getTransactionId().equals(transactionId)));
    }


    public List<Transaction> getTransactions(String accountNumber) {
        return transactionTable.get(accountNumber);
    }

    private String createTransactionId() {
       return  UUID.randomUUID().toString();
    }

}
