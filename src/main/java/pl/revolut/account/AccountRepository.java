package pl.revolut.account;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AccountRepository {

    private Map<String,Account> accountTable = new ConcurrentHashMap<>();

    public Account save(Account account) {
        accountTable.put(account.getNumber(),account);
        return account;
    }

    public Optional<Account> retrieve(String accountNumber) {
        return Optional.ofNullable(accountTable.get(accountNumber));
    }

    public Optional<List<Account>> list() {
        return  Optional.of(new ArrayList<>(accountTable.values()));
    }

    public Optional<Account> modify(String accountNumber,Account account) {
        if(!accountTable.containsKey(accountNumber)) {
            return Optional.empty();
        }
        accountTable.put(accountNumber,account);
        return Optional.of(account);
    }

    public void delete(String accountNumber) {
        accountTable.remove(accountNumber);
    }

    public boolean exists(String accountNumber) {
       return accountTable.containsKey(accountNumber);
    }



}
