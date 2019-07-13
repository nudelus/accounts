package pl.revolut.account;

import pl.revolut.util.JsonHelper;
import pl.revolut.util.JsonTransformer;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Optional;

import static spark.Spark.*;

public class AccountController {

    private AccountRepository accountRepository = new AccountRepository();

    public AccountController() {
        init();
    }

    public void init() {
        get("/accounts", getAccounts ,new JsonTransformer());
        get("/accounts/:number", getAccounts ,new JsonTransformer());
        post("/accounts", saveAccount ,new JsonTransformer());
        delete("/accounts/:number", deleteAccount);
        put("/accounts/:number", modifyAccount ,new JsonTransformer());

    }

    private Route getAccounts = (Request request, Response response) -> {
        String accountNumber = request.params(":number");
        if(accountNumber != null) {

            Optional<Account> account = accountRepository.retrieve(accountNumber);
            account.orElseThrow(() ->new AccountNotFoundException());
            return account.get();
        }

        return accountRepository.list().get();

    };

    private Route saveAccount = (Request request, Response response) -> accountRepository.save(JsonHelper.read(request.body(),Account.class));

    private Route deleteAccount = (Request request, Response response) -> {
        String accountNumber = request.params(":number");
        if(!accountRepository.exists(accountNumber)) {
            throw new AccountNotFoundException();
        }
        accountRepository.delete(accountNumber);
        return "";
    };


    private Route modifyAccount = (Request request, Response response) -> {
        String accountNumber = request.params(":number");
        if(!accountRepository.exists(accountNumber)) {
            throw new AccountNotFoundException();
        }

        Account account = JsonHelper.read(request.body(),Account.class);
        return accountRepository.modify(accountNumber,account).get();
    };

}
