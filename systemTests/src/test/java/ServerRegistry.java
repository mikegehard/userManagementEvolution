import java.util.HashMap;
import java.util.Map;

/**
 * Created by pivotal on 10/16/15.
 */
public class ServerRegistry {
    public static final String BILLING = "billing";
    public static final String UMS = "ums";
    public static final String EMAIL = "email";

    private static final Map<String, ServerDetails> servers = new HashMap<>();

    static {
        servers.put(BILLING, new ServerDetails("billing-ripping-papalization", "'counter.billing.reocurringPayment.created'"));
        servers.put(EMAIL, new ServerDetails("email-edictal-buffo", "'counter.emails.sent'"));
        servers.put(UMS, new ServerDetails("ums-tidy-taipan", "'counter.ums.subscription.created'"));
    }

    public ServerDetails get(String server) {
        return servers.get(server);
    }
}
