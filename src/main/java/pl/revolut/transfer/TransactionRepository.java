package pl.revolut.transfer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

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
        transactionTable.entrySet().forEach(e -> e.getValue().removeIf(t -> t.getTransactionId().equals(transactionId)));
    }

    private String createTransactionId() {
       return  UUID.randomUUID().toString();
    }

}
