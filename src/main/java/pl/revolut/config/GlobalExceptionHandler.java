package pl.revolut.config;

import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.revolut.account.AccountNotFoundException;
import pl.revolut.transfer.InsufficientFundsException;
import pl.revolut.transfer.TransferException;
import pl.revolut.util.ErrorMessage;
import pl.revolut.util.JsonHelper;

import static spark.Spark.exception;

/**
 * The Global exception handler to transform application exceptions to json object
 */
public final class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public static void init() {
        exception(AccountNotFoundException.class, (exception, request, response) -> {
            response.status(HttpStatus.BAD_REQUEST_400);

            ErrorMessage errorMessage = new ErrorMessage(exception.getMessage());
            LOGGER.error(exception.getMessage());
            response.body(JsonHelper.write(errorMessage));
        });

        exception(InsufficientFundsException.class, (exception, request, response) -> {
            response.status(HttpStatus.BAD_REQUEST_400);

            ErrorMessage errorMessage = new ErrorMessage(exception.getMessage());
            LOGGER.error(exception.getMessage());
            response.body(JsonHelper.write(errorMessage));
        });

        exception(TransferException.class, (exception, request, response) -> {
            response.status(HttpStatus.BAD_REQUEST_400);

            ErrorMessage errorMessage = new ErrorMessage(exception.getMessage());
            LOGGER.error(exception.getMessage());
            response.body(JsonHelper.write(errorMessage));
        });

        exception(Exception.class, (exception, request, response) -> {
            response.status(HttpStatus.INTERNAL_SERVER_ERROR_500);

            ErrorMessage errorMessage = new ErrorMessage(exception.getMessage());
            LOGGER.error(exception.getMessage());
            response.body(JsonHelper.write(errorMessage));
        });
    }


}
