package pl.revolut.account;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String accountNumber) {
        super("Account not found for accountNumber : "+accountNumber);
    }
}
