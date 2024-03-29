package pl.revolut.data;

import pl.revolut.account.Account;
import pl.revolut.account.AccountType;
import pl.revolut.transfer.Transfer;

import java.math.BigDecimal;

public class DataProvider {

    public static Account createEuroAccount() {
        Account account = new Account();
        account.setBalance(new BigDecimal(3000));
        account.setAccountNumber("1234-1234-1234");
        account.setAccountType(AccountType.STANDARD);
        account.setCurrency("EUR");
        account.setCustomerId("Customer01");

        return account;
    }

    public static Account createForintAccount() {
        Account account = new Account();
        account.setBalance(new BigDecimal(60000));
        account.setAccountNumber("5678-5678-5678");
        account.setAccountType(AccountType.STANDARD);
        account.setCurrency("HUF");
        account.setCustomerId("Customer02");

        return account;
    }

    public static Account createJenAccount() {
        Account account = new Account();
        account.setBalance(new BigDecimal(6000));
        account.setAccountNumber("2222-2222-2222");
        account.setAccountType(AccountType.STANDARD);
        account.setCurrency("JEN");
        account.setCustomerId("Customer03");

        return account;
    }


    public static Transfer createTransfer() {
        Transfer transfer = new Transfer();
        transfer.setAmount(new BigDecimal(6000));
        transfer.setCurrency("HUF");
        transfer.setSourceAccountNumber("5678-5678-5678");
        transfer.setTargetAccountNumber("1234-1234-1234");

        return transfer;
    }

    public static Transfer createJenTransfer() {
        Transfer transfer = new Transfer();
        transfer.setAmount(new BigDecimal(300));
        transfer.setCurrency("EUR");
        transfer.setSourceAccountNumber("1234-1234-1234");
        transfer.setTargetAccountNumber("2222-2222-2222");

        return transfer;
    }
}
