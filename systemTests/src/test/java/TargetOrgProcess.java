import java.io.IOException;

public class TargetOrgProcess {
    public Process run(String org) throws IOException {
        return new ProcessBuilder(
                "cf",
                "target",
                "-o",
                org
        ).inheritIO().start();
    }
}
