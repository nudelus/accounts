package pl.revolut.transfer;

import pl.revolut.util.JsonHelper;
import pl.revolut.util.JsonTransformer;
import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.post;

/**
 * The Transfer controller object to make transfer between accounts
 */
public class TransferController {

    private TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
        initRouting();
    }

    private void initRouting() {
        post("/transfer", makeTransfer, new JsonTransformer());
    }


    private Route makeTransfer = (Request request, Response response) -> {
       Transfer transfer = JsonHelper.read(request.body(),Transfer.class);
       transferService.makeTransfer(transfer);
       return "";
    };

}
