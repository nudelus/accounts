package pl.revolut.transfer;

public class TransferException extends RuntimeException {

    public TransferException() {
        super("Error occurred during transfer");
    }
}
