import org.assertj.core.data.MapEntry;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.MapEntry.entry;
import static org.hamcrest.Matchers.equalTo;

public class SystemTest {
            /* TODO:
            Open questions:
            Is there a way speed up the deploy cycle?
            Can you just run the tests without pushing apps?
            Do you need to delete apps at the end?
            How can you push just one service?
            How can you run them locally as well as remotely?

         */

    static ServerRegistry registry = new ServerRegistry("red.springapps.io");
    static String manifestLocation = "../manifest-red-test.yml";

    @BeforeClass
    public static void setup() throws IOException, InterruptedException {
        Process targetTestingOrg = new TargetOrgProcess().run("gehardville");
        targetTestingOrg.waitFor();
        assertThat(targetTestingOrg.exitValue()).isEqualTo(0);

        Process targetTestingSpace = new TargetSpaceProcess().run("test");
        targetTestingSpace.waitFor();
        assertThat(targetTestingSpace.exitValue()).isEqualTo(0);

        /* TODO: create services that you need
        - config-server - must configure via the UI
        - service-registry
        - rabbit-mq
        - circuit-breaker
        */
    }

    @AfterClass
    public static void teardown() throws IOException, InterruptedException {
        // TODO: Add these in when you figure out how to automatically create/configure services
//        Process deleteConfigService = new DeleteServiceProcess().run("config-server");
//        deleteConfigService.waitFor();
//        assertThat(deleteConfigService.exitValue()).isEqualTo(0);
//
//        Process deleteServiceDiscoveryService = new DeleteServiceProcess().run("service-discovery");
//        deleteServiceDiscoveryService.waitFor();
//        assertThat(deleteServiceDiscoveryService.exitValue()).isEqualTo(0);

        Process targetDevelopmentSpace = new TargetSpaceProcess().run("development");
        targetDevelopmentSpace.waitFor();
        assertThat(targetDevelopmentSpace.exitValue()).isEqualTo(0);
    }

    @Test
    public void billingService() throws InterruptedException, IOException {
        Process deleteApp = new DeleteAppProcess().run(ServerRegistry.BILLING);
        deleteApp.waitFor();
        assertThat(deleteApp.exitValue()).isEqualTo(0);

        Process deployToCloudFoundry = new DeployProcess().start(ServerRegistry.BILLING, manifestLocation);
        deployToCloudFoundry.waitFor();

        assertThat(deployToCloudFoundry.exitValue()).isEqualTo(0);

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

    @Test
    public void endToEnd() throws InterruptedException, IOException {
        List<String> appsToPush = new ArrayList<>();
        appsToPush.add(ServerRegistry.BILLING);
        appsToPush.add(ServerRegistry.EMAIL);
        appsToPush.add(ServerRegistry.UMS);

        Process deleteApp;

        for(String app : appsToPush) {
            deleteApp = new DeleteAppProcess().run(app);
            deleteApp.waitFor();
            assertThat(deleteApp.exitValue()).isEqualTo(0);
        }

        // TODO: Is there a way to run deploys (one for each) in parallel and wait until all exit codes are 0??
        Process deployToCloudFoundry = new DeployProcess().startAll(manifestLocation);
        deployToCloudFoundry.waitFor();
        assertThat(deployToCloudFoundry.exitValue()).isEqualTo(0);

        Map<String, Integer> initialValues = new HashMap<>();
        for(String app : appsToPush) {
            initialValues.put(app, registry.get(app).getMetricValue());
        }

        given()
                .contentType("application/json;charset=UTF-8")
                .body("{\"userId\": \"abc123\", \"packageId\": \"package123\"}")
                .when()
                .post(registry.get(ServerRegistry.UMS).getUrl() + "/subscriptions")
                .then()
                .statusCode(201)
                .body("acknowledged", equalTo(true));

        Map<String, Integer> actual = new HashMap<>();
        for(String app : appsToPush) {
            actual.put(app, registry.get(app).getMetricValue());
        }

        MapEntry[] expected = new MapEntry[appsToPush.size()];
        int i = 0;
        for(String app : appsToPush) {
            expected[i] = entry(app, initialValues.get(app) + 1);
            i++;
        }

        assertThat(actual).containsOnly(expected);
    }
}
