package pl.revolut.transfer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TransactionRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionRepository.class);

    private Map<String, List<Transaction>> transactionTable = new ConcurrentHashMap<>();


    public void saveTransaction(Transaction transaction) {

    }



}
