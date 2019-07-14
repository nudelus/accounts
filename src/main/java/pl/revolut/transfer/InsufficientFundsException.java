package pl.revolut.transfer;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(String accountNumber) {
        super("Insufficient Funds on account : "+accountNumber);
    }

}
