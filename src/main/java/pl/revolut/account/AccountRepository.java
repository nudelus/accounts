package pl.revolut.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The Account repository, simple map based in memory data store for accounts
 */
public class AccountRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountRepository.class);

    private Map<String,Account> accountTable = new ConcurrentHashMap<>();

    public Account save(Account account) {
        account.setCreationDate(LocalDateTime.now());
        accountTable.put(account.getAccountNumber(),account);
        LOGGER.debug("Account saved : {}",account);
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
        Account oldAccount = accountTable.get(accountNumber);

        account.setCreationDate(oldAccount.getCreationDate());

        LOGGER.debug("Account modified from : {}",oldAccount);
        LOGGER.debug("Account modified to : {}",account);
        accountTable.put(accountNumber,account);
        return Optional.of(account);
    }

    public void delete(String accountNumber) {
        accountTable.remove(accountNumber);
        LOGGER.debug("Account for accountNumber {} deleted ",accountNumber);
    }

    public boolean exists(String accountNumber) {
       return accountTable.containsKey(accountNumber);
    }



}
