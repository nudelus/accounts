package pl.revolut.util;

import pl.revolut.account.AccountController;
import pl.revolut.account.AccountRepository;
import pl.revolut.transfer.TransactionRepository;
import pl.revolut.transfer.TransferController;
import pl.revolut.transfer.TransferService;

public class DependencyInjection {

    public static void createControllerInstances(){
        TransactionRepository transactionRepository = new TransactionRepository();
        AccountRepository accountRepository = new AccountRepository();

        new AccountController(accountRepository);
        new TransferController(new TransferService(accountRepository,transactionRepository));
    }
}
