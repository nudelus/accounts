package pl.revolut;

import pl.revolut.account.AccountController;
import pl.revolut.config.SparkConfig;



public class Application {

    public static void main(String[] args) {
        SparkConfig.init();

        new AccountController();
    }
}
