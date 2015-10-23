import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class DeployProcess {

    private final List<String> appsToPush;
    private final String manifestLocation;
    private final List<Process> processes = new ArrayList<>();

    public DeployProcess(List<String> appsToPush, String manifestLocation) {
        this.appsToPush = appsToPush;
        this.manifestLocation = manifestLocation;
    }

    public void run() throws IOException, InterruptedException {
        for(String app: appsToPush) {
            try{
                processes.add(startProcess(app, manifestLocation));
            } catch (IOException e) {
                // just ignore it as it should never happen
            }
        }

        waitFor();
    }

    public int statusCode() {
        return this.processes.stream().mapToInt(Process::exitValue).sum();
    }

    private void waitFor() throws InterruptedException {
        while (running()){
            Thread.sleep(1000);
        }
    }

    private Process startProcess(String app, String manifestLocation) throws IOException {
        List<String> commands = new ArrayList<>();
        commands.add("cf");
        commands.add("push");

        commands.add(app);

        commands.add("-f");
        commands.add(manifestLocation);

        return new ProcessBuilder(commands).inheritIO().start();
    }

    private boolean running() {
        return this.processes.stream().filter(Process::isAlive).collect(toList()).size() > 0;
    }
}
