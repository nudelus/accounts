package pl.revolut.transaction;

import pl.revolut.util.JsonTransformer;
import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.get;

public class TransactionController {

    private TransactionRepository transactionRepository;

    public TransactionController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
        initRouting();
    }

    private void initRouting() {
        get("/transactions/:accountNumber", getTransactions, new JsonTransformer());
    }


    private Route getTransactions = (Request request, Response response) -> {
        String accountNumber = request.params(":accountNumber");
        return transactionRepository.getTransactions(accountNumber);
    };
}
