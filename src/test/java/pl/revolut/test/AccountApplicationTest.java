package pl.revolut.test;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.HttpContentResponse;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.*;
import pl.revolut.Application;
import pl.revolut.account.Account;
import pl.revolut.data.DataProvider;
import pl.revolut.transaction.Transaction;
import pl.revolut.transaction.TransactionType;
import pl.revolut.transfer.Transfer;
import pl.revolut.util.JsonHelper;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static spark.Spark.awaitInitialization;
import static spark.Spark.stop;

public class AccountApplicationTest {

    private HttpClient httpClient;
    private static final String ACCOUNTS_URL = "http://localhost:8080/accounts";
    private static final String TRANSACTIONS_URL = "http://localhost:8080/transactions";
    private static final String TRANSFER_URL = "http://localhost:8080/transfer";
    private static final String APPLICATION_JSON = "application/json";


    @BeforeClass
    public static void init() {
        String[] args = {};
        Application.main(args);
        awaitInitialization();
    }


    @Before
    public void initClient() throws Exception {
        httpClient = new HttpClient();
        httpClient.start();
    }


    @Test
    public void testAccountSaveProcess() throws Exception {
        Account account = DataProvider.createEuroAccount();

        createAccount(account);

        checkAccountListSize(1);
        checkAccountResponse(account, HttpStatus.OK_200);

        deleteAccount(account);

        checkAccountResponse(account, HttpStatus.BAD_REQUEST_400);

    }


    @Test
    public void testTransfer() throws Exception {
        initData();
        Transfer transfer = DataProvider.createTransfer();

        String sourceAccountNumber = transfer.getSourceAccountNumber();
        String targetAccountNumber = transfer.getTargetAccountNumber();

        Account sourceAccount = getAccount(sourceAccountNumber);
        Account targetAccount = getAccount(targetAccountNumber);

        makeTransfer(transfer);

        checkTransaction(sourceAccountNumber, targetAccountNumber, TransactionType.SPENDING, transfer);
        checkTransaction(targetAccountNumber, sourceAccountNumber, TransactionType.INCOME, transfer);

        Account sourceAccountAfterTransfer = getAccount(sourceAccountNumber);
        Account targetAccountAfterTransfer = getAccount(targetAccountNumber);


    }

    private void initData() throws Exception {
        createAccount(DataProvider.createEuroAccount());
        createAccount(DataProvider.createForintAccount());
    }

    private void checkAccountResponse(Account account, int httpStatus) throws Exception {
        HttpContentResponse accountResponse = (HttpContentResponse) httpClient.GET(ACCOUNTS_URL + "/" + account.getAccountNumber());
        assertThat("Response code not valid", accountResponse.getStatus(), is(httpStatus));
        if (HttpStatus.OK_200 == httpStatus) {
            String jsonResponse = new String(accountResponse.getContent());
            Account responseAccount = JsonHelper.read(jsonResponse, Account.class);
            assertThat("Response account not equals base account", responseAccount, equalTo(account));
        }
    }

    private void checkAccountListSize(int size) throws Exception {
        HttpContentResponse listResponse = (HttpContentResponse) httpClient.GET(ACCOUNTS_URL);
        assertThat("Response code not valid", listResponse.getStatus(), is(HttpStatus.OK_200));
        String jsonListResponse = new String(listResponse.getContent());
        Account[] responseAccountList = JsonHelper.read(jsonListResponse, Account[].class);
        assertThat("Account List size no valid", responseAccountList.length, is(size));
    }

    private void deleteAccount(Account account) throws Exception {
        ContentResponse deleteResponse = httpClient.newRequest(ACCOUNTS_URL + "/" + account.getAccountNumber()).method(HttpMethod.DELETE).send();
        assertThat("Response code not valid", deleteResponse.getStatus(), is(HttpStatus.OK_200));
    }

    private void createAccount(Account account) throws Exception {
        ContentResponse postResponse = httpClient.newRequest(ACCOUNTS_URL)
                .method(HttpMethod.POST)
                .header(HttpHeader.ACCEPT, APPLICATION_JSON)
                .header(HttpHeader.CONTENT_TYPE, APPLICATION_JSON)
                .content(new StringContentProvider(JsonHelper.write(account)), APPLICATION_JSON)
                .send();

        assertThat("Response code not valid", postResponse.getStatus(), is(HttpStatus.OK_200));
    }

    private Account getAccount(String accountNumber) throws Exception {
        HttpContentResponse accountResponse = (HttpContentResponse) httpClient.newRequest(ACCOUNTS_URL + "/" + accountNumber)
                .method(HttpMethod.GET)
                .header(HttpHeader.ACCEPT, APPLICATION_JSON)
                .send();

        assertThat("Response code not valid", accountResponse.getStatus(), is(HttpStatus.OK_200));
        return JsonHelper.read(new String(accountResponse.getContent()), Account.class);
    }

    private void checkTransaction(String accountNumber, String targetAccountNumber, TransactionType transactionType, Transfer transfer) throws Exception {
        HttpContentResponse transactionsResponse = (HttpContentResponse) httpClient.newRequest(TRANSACTIONS_URL + "/" + accountNumber)
                .method(HttpMethod.GET)
                .header(HttpHeader.ACCEPT, APPLICATION_JSON)
                .send();

        assertThat("Response code not valid", transactionsResponse.getStatus(), is(HttpStatus.OK_200));


        String jsonListResponse = new String(transactionsResponse.getContent());
        Transaction[] responseTransactionList = JsonHelper.read(jsonListResponse, Transaction[].class);
        assertThat("Account List size no valid", responseTransactionList.length, is(1));
        Transaction firstTransaction = responseTransactionList[0];

        assertThat("Transaction Type no valid", firstTransaction.getTransactionType(), is(transactionType));
        assertThat("Transaction Amount no valid", firstTransaction.getAmountInOriginalCurrency(), is(transfer.getAmount()));
        assertThat("Transaction Account no valid", firstTransaction.getAccountNumber(), is(accountNumber));
        assertThat("Transaction Partner Account no valid", firstTransaction.getPartnerAccountNumber(), is(targetAccountNumber));
        assertThat("Transaction date is null", firstTransaction.getTransactionDate(), is(notNullValue()));
    }

    private void makeTransfer(Transfer transfer) throws Exception {
        ContentResponse transferResponse = httpClient.newRequest(TRANSFER_URL)
                .method(HttpMethod.POST)
                .header(HttpHeader.ACCEPT, APPLICATION_JSON)
                .header(HttpHeader.CONTENT_TYPE, APPLICATION_JSON)
                .content(new StringContentProvider(JsonHelper.write(transfer)), APPLICATION_JSON)
                .send();

        assertThat("Response code not valid", transferResponse.getStatus(), is(HttpStatus.OK_200));

    }

    @After
    public void stopClient() throws Exception {
        httpClient.stop();
    }

    @AfterClass
    public static void after() {
        stop();
    }
}