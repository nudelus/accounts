package pl.revolut.data;

import pl.revolut.account.Account;
import pl.revolut.account.AccountType;
import pl.revolut.transfer.Transfer;

public class DataProvider {

    public static Account createEuroAccount() {
        Account account = new Account();
        account.setBalance(3000d);
        account.setAccountNumber("1234-1234-1234");
        account.setAccountType(AccountType.STANDARD);
        account.setCurrency("EUR");
        account.setCustomerId("Customer01");

        return account;
    }

    public static Account createForintAccount() {
        Account account = new Account();
        account.setBalance(60000d);
        account.setAccountNumber("5678-5678-5678");
        account.setAccountType(AccountType.STANDARD);
        account.setCurrency("HUF");
        account.setCustomerId("Customer02");

        return account;
    }

    public static Transfer createTransfer() {
        Transfer transfer = new Transfer();
        transfer.setAmount(6000d);
        transfer.setCurrency("HUF");
        transfer.setSourceAccountNumber("5678-5678-5678");
        transfer.setTargetAccountNumber("1234-1234-1234");

        return transfer;
    }
}
