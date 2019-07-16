package pl.revolut.transfer;

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
import pl.revolut.util.JsonHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static spark.Spark.awaitInitialization;
import static spark.Spark.stop;

public class TransferControllerTest {

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
        initData();
    }

    private void initData() throws Exception {
        httpClient.newRequest(ACCOUNTS_URL)
                .method(HttpMethod.POST)
                .header(HttpHeader.ACCEPT, APPLICATION_JSON)
                .header(HttpHeader.CONTENT_TYPE, APPLICATION_JSON)
                .content(new StringContentProvider(JsonHelper.write(DataProvider.createEuroAccount())), APPLICATION_JSON)
                .send();

        httpClient.newRequest(ACCOUNTS_URL)
                .method(HttpMethod.POST)
                .header(HttpHeader.ACCEPT, APPLICATION_JSON)
                .header(HttpHeader.CONTENT_TYPE, APPLICATION_JSON)
                .content(new StringContentProvider(JsonHelper.write(DataProvider.createForintAccount())), APPLICATION_JSON)
                .send();

    }

    @Test
    public void testTransfer() throws Exception {
        Transfer transfer = DataProvider.createTransfer();

        String sourceAccountNumber = transfer.getSourceAccountNumber();
        String targetAccountNumber = transfer.getTargetAccountNumber();

        Account sourceAccount = getAccount(sourceAccountNumber);
        Account targetAccount = getAccount(targetAccountNumber);

        makeTransfer(transfer);

        checkTransaction(sourceAccountNumber,targetAccountNumber,TransactionType.SPENDING,transfer);
        checkTransaction(targetAccountNumber,sourceAccountNumber,TransactionType.INCOME,transfer);

        Account sourceAccountAfterTransfer = getAccount(sourceAccountNumber);
        Account targetAccountAfterTransfer = getAccount(targetAccountNumber);




    }

    private Account getAccount(String accountNumber) throws Exception {
        HttpContentResponse accountResponse = (HttpContentResponse)httpClient.newRequest(ACCOUNTS_URL + "/" +accountNumber)
                .method(HttpMethod.GET)
                .header(HttpHeader.ACCEPT, APPLICATION_JSON)
                .send();

        assertThat("Response code not valid", accountResponse.getStatus(), is(HttpStatus.OK_200));
        return JsonHelper.read(new String(accountResponse.getContent()), Account.class);
    }

    private void checkTransaction(String accountNumber,String targetAccountNumber,TransactionType transactionType,Transfer transfer) throws Exception {
        HttpContentResponse transactionsResponse = (HttpContentResponse)httpClient.newRequest(TRANSACTIONS_URL + "/" +accountNumber)
                .method(HttpMethod.GET)
                .header(HttpHeader.ACCEPT, APPLICATION_JSON)
                .send();

        assertThat("Response code not valid", transactionsResponse.getStatus(), is(HttpStatus.OK_200));


        String jsonListResponse = new String(transactionsResponse.getContent());
        Transaction[] responseTransactionList = JsonHelper.read(jsonListResponse, Transaction[].class);
        assertThat("Account List size no valid", responseTransactionList.length, is(1));
        Transaction firstTransaction = responseTransactionList[0];

        assertThat("Transaction Type no valid",firstTransaction.getTransactionType(),is(transactionType));
        assertThat("Transaction Amount no valid",firstTransaction.getAmountInOriginalCurrency(),is(transfer.getAmount()));
        assertThat("Transaction Account no valid",firstTransaction.getAccountNumber(),is(accountNumber));
        assertThat("Transaction Partner Account no valid",firstTransaction.getPartnerAccountNumber(),is(targetAccountNumber));
        assertThat("Transaction date is null",firstTransaction.getTransactionDate(),is(notNullValue()));
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