package pl.revolut.util;

import pl.revolut.account.AccountController;
import pl.revolut.account.AccountRepository;
import pl.revolut.transaction.TransactionController;
import pl.revolut.transaction.TransactionRepository;
import pl.revolut.transfer.TransferController;
import pl.revolut.transfer.TransferService;

/**
 * The Dependency Injection to create the necessary objects and their dependencies
 */
public class DependencyInjection {

    public static void createControllerInstances(){
        TransactionRepository transactionRepository = new TransactionRepository();
        AccountRepository accountRepository = new AccountRepository();

        new AccountController(accountRepository);
        new TransactionController(transactionRepository);
        new TransferController(new TransferService(accountRepository,transactionRepository));
    }
}
