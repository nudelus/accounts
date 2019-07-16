package pl.revolut.account;

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
import pl.revolut.util.JsonHelper;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static spark.Spark.awaitInitialization;
import static spark.Spark.stop;

public class AccountControllerTest {

    private HttpClient httpClient;
    private static final String ACCOUNTS_URL = "http://localhost:8080/accounts";
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

        ContentResponse postResponse = httpClient.newRequest(ACCOUNTS_URL)
                .method(HttpMethod.POST)
                .header(HttpHeader.ACCEPT, APPLICATION_JSON)
                .header(HttpHeader.CONTENT_TYPE, APPLICATION_JSON)
                .content(new StringContentProvider(JsonHelper.write(account)), APPLICATION_JSON)
                .send();

        assertThat("Response code not valid", postResponse.getStatus(), is(HttpStatus.OK_200));


        HttpContentResponse listResponse = (HttpContentResponse) httpClient.GET(ACCOUNTS_URL);
        assertThat("Response code not valid", listResponse.getStatus(), is(HttpStatus.OK_200));
        String jsonListResponse = new String(listResponse.getContent());
        List responseAccountList = JsonHelper.read(jsonListResponse, ArrayList.class);
        assertThat("Account List size no valid", responseAccountList.size(), is(1));

        HttpContentResponse accountResponse = (HttpContentResponse) httpClient.GET(ACCOUNTS_URL + "/" + account.getAccountNumber());
        assertThat("Response code not valid", accountResponse.getStatus(), is(HttpStatus.OK_200));
        String jsonResponse = new String(accountResponse.getContent());
        Account responseAccount = JsonHelper.read(jsonResponse, Account.class);
        assertThat("Response account not equals base account", responseAccount, equalTo(account));

        ContentResponse deleteResponse = httpClient.newRequest(ACCOUNTS_URL + "/" + account.getAccountNumber()).method(HttpMethod.DELETE).send();
        assertThat("Response code not valid", deleteResponse.getStatus(), is(HttpStatus.OK_200));

        HttpContentResponse responseAfterDelete = (HttpContentResponse) httpClient.GET(ACCOUNTS_URL + "/" + account.getAccountNumber());
        assertThat("Response code not valid", responseAfterDelete.getStatus(), is(HttpStatus.BAD_REQUEST_400));
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
