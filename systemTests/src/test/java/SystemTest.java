import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SystemTest {

    private static final String BILLING_SERVER = "billing-ripping-papalization";
    private static final String EMAIL_SERVER = "email-edictal-buffo";
    private static final String UMS_SERVER = "ums-tidy-taipan";

    public static final String PCF_APP_DOMAIN = "apps.teal.springapps.io";

    private static final Map<String, String> metrics = new HashMap<>();

    static {
        metrics.put(BILLING_SERVER, "'counter.billing.reocurringPayment.created'");
        metrics.put(EMAIL_SERVER, "'counter.emails.sent'");
        metrics.put(UMS_SERVER, "'counter.ums.subscription.created'");
    }

    @Test
    public void endToEnd() throws InterruptedException, IOException {
        /* TODO:
            How do you not push any services but just run the tests?
            How can you push just one service?

         */
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

        int billingInitialValue = getMetricValue(BILLING_SERVER);
        int subscriptionInitialValue = getMetricValue(UMS_SERVER);
        int emailInitialValue = getMetricValue(EMAIL_SERVER);

        given()
                .contentType("application/json;charset=UTF-8")
                .body("{\"userId\": \"abc123\", \"packageId\": \"package123\"}")
                .when()
                .post(serverUrl(UMS_SERVER) + "/subscriptions")
                .then()
                .statusCode(201)
                .body("acknowledged", equalTo(true));

        assertThat(getMetricValue(BILLING_SERVER)).isEqualTo(billingInitialValue + 1);
        assertThat(getMetricValue(UMS_SERVER)).isEqualTo(subscriptionInitialValue + 1);
        assertThat(getMetricValue(EMAIL_SERVER)).isEqualTo(emailInitialValue + 1);
    }

    private String serverUrl(String appName) {
        return "http://" + appName + "." + PCF_APP_DOMAIN;
    }

    private int getMetricValue(String appName) {
        try {
            return get(
                    serverUrl(appName) + "/metrics"
            )
                    .then()
                    .extract().body().path(metrics.get(appName));
        } catch (NullPointerException e) {
            return 0;
        }
    }
}
