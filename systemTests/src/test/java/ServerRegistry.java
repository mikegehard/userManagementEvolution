import java.util.HashMap;
import java.util.Map;

public class ServerRegistry {
    public static final String BILLING = "billing-test";
    public static final String UMS = "user-management-test";
    public static final String EMAIL = "email-test";

    private static final Map<String, ServerDetails> servers = new HashMap<>();

    public ServerRegistry(String pcfAppDomain) {
        servers.put(BILLING, new ServerDetails(BILLING + "." + pcfAppDomain, "'counter.billing.reocurringPayment.created'"));
        servers.put(EMAIL, new ServerDetails(EMAIL + "." + pcfAppDomain, "'counter.emails.sent'"));
        servers.put(UMS, new ServerDetails(UMS + "." + pcfAppDomain, "'counter.ums.subscription.created'"));
    }

    public ServerDetails get(String server) {
        return servers.get(server);
    }
}
