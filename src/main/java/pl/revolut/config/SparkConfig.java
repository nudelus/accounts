package pl.revolut.config;

import org.eclipse.jetty.http.HttpStatus;
import pl.revolut.account.AccountNotFoundException;
import pl.revolut.util.ErrorMessage;
import pl.revolut.util.JsonHelper;

import static spark.Spark.*;

public class SparkConfig {

    public static void init() {
        port(4567);

        after((request, response) ->
            response.type("application/json")
        );

        exception(AccountNotFoundException.class,(exception, request, response) -> {
            response.status(HttpStatus.BAD_REQUEST_400);

            ErrorMessage errorMessage = new ErrorMessage(exception.getMessage());

            response.body(JsonHelper.write(errorMessage));
        });

    }
}
