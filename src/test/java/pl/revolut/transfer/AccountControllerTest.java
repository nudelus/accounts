package pl.revolut.transfer;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.HttpContentResponse;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.*;
import pl.revolut.Application;
import pl.revolut.account.AccountController;
import pl.revolut.account.AccountRepository;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static spark.Spark.awaitInitialization;
import static spark.Spark.stop;

public class AccountControllerTest {

    private HttpClient httpClient;

    @BeforeClass
    public static void init()  {
        String [] args = {};
        Application.main(args);
        awaitInitialization();
    }


    @Before
    public void initClient() throws Exception {
        httpClient = new HttpClient();
        httpClient.start();
    }

    @Test
    public void testSave() throws Exception {


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
