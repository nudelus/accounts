package pl.revolut;

import pl.revolut.config.GlobalExceptionHandler;
import pl.revolut.config.SparkConfig;
import pl.revolut.util.DependencyInjection;

public class Application {

    public static void main(String[] args) {
        SparkConfig.init();
        GlobalExceptionHandler.init();
        DependencyInjection.createControllerInstances();

    }


}
