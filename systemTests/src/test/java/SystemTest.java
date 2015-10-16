import org.junit.Test;

import java.io.IOException;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SystemTest {
    @Test
    public void endToEnd() throws InterruptedException, IOException {
        /* TODO:
            How can you only do a full push before all tests?
            How do you not push any services but just run the tests?
            How can you push just one service?
            How can you run them locally as well as remotely?

         */
        ServerRegistry registry = new ServerRegistry();

        Process deployToCloudFoundry = new ProcessBuilder(
                "cf",
                "push",
                "-f",
                "../manifest-teal.yml"
        )
                .inheritIO()
                .start();
        deployToCloudFoundry.waitFor();

        assertThat(deployToCloudFoundry.exitValue()).isEqualTo(0);

        ServerDetails billingServer = registry.get(ServerRegistry.BILLING);
        ServerDetails umsServer = registry.get(ServerRegistry.UMS);
        ServerDetails emailServer = registry.get(ServerRegistry.EMAIL);

        int billingInitialValue = billingServer.getMetricValue();
        int subscriptionInitialValue = umsServer.getMetricValue();
        int emailInitialValue = emailServer.getMetricValue();

        given()
                .contentType("application/json;charset=UTF-8")
                .body("{\"userId\": \"abc123\", \"packageId\": \"package123\"}")
                .when()
                .post(umsServer.getUrl() + "/subscriptions")
                .then()
                .statusCode(201)
                .body("acknowledged", equalTo(true));

        assertThat(billingServer.getMetricValue()).isEqualTo(billingInitialValue + 1);
        assertThat(umsServer.getMetricValue()).isEqualTo(subscriptionInitialValue + 1);
        assertThat(emailServer.getMetricValue()).isEqualTo(emailInitialValue + 1);
    }
}
