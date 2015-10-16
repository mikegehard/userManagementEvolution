import org.junit.Test;

import java.io.IOException;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class BillingTest {

    @Test
    public void endToEnd() throws InterruptedException, IOException {
        Process deployToCloudFoundry = new ProcessBuilder(
                "cf",
                "push",
                "billing",
                "-f",
                "../manifest-teal.yml"
        )
                .inheritIO()
                .start();
        deployToCloudFoundry.waitFor();

        assertThat(deployToCloudFoundry.exitValue()).isEqualTo(0);

        ServerRegistry registry = new ServerRegistry();
        ServerDetails billingServer = registry.get(ServerRegistry.BILLING);
        int billingInitialValue = billingServer.getMetricValue();

        given()
                .contentType("application/json;charset=UTF-8")
                .body("{\"amount\": 100}")
                .when()
                .post(billingServer.getUrl() + "/reocurringPayment")
                .then()
                .statusCode(201);

        assertThat(billingServer.getMetricValue()).isEqualTo(billingInitialValue + 1);
    }

}
