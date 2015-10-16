import static com.jayway.restassured.RestAssured.get;

public class ServerDetails{
    private final String serverName;
    private final String metricName;
    private static final String PCF_APP_DOMAIN = "apps.teal.springapps.io";


    public ServerDetails(String serverName, String metricName) {
        this.serverName = serverName;
        this.metricName = metricName;
    }

    private String getServerName() {
        return serverName;
    }

    private String getMetricName() {
        return metricName;
    }

    public String getUrl() {
        return "http://" + getServerName() + "." + PCF_APP_DOMAIN;
    }

    public int getMetricValue() {
        try {
            return get(
                    getUrl() + "/metrics"
            )
                    .then()
                    .extract().body().path(getMetricName());
        } catch (NullPointerException e) {
            return 0;
        }
    }
}
