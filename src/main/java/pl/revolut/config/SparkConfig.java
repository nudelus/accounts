package pl.revolut.config;

import static spark.Spark.after;
import static spark.Spark.port;

/**
 * The Spark server configuration
 */
public final class SparkConfig {

    public static void init() {
        port(8080);
        after((request, response) ->
                response.type("application/json")
        );


    }
}
