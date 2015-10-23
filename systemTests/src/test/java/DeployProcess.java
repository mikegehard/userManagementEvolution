import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeployProcess {
    public Process startAll(String manifestLocation) throws IOException {
        return startProcess(new ArrayList<>(), manifestLocation);
    }

    public Process start(String appToPush, String manifestLocation) throws IOException {
        List<String> app = new ArrayList<>();
        app.add(appToPush);

        return startProcess(app, manifestLocation);
    }

    private Process startProcess(List<String> appsToPush, String manifestLocation) throws IOException {
        List<String> commands = new ArrayList<>();
        commands.add("cf");
        commands.add("push");

        commands.addAll(appsToPush);

        commands.add("-f");
        commands.add(manifestLocation);

        return new ProcessBuilder(commands).inheritIO().start();
    }
}
