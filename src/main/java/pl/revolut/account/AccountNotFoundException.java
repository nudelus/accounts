package pl.revolut.account;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException() {
        super("Account Not Found");
    }
}
