import java.io.IOException;

public class DeleteAppProcess {
    public Process run(String appName) throws IOException {
        return new ProcessBuilder(
                "cf",
                "delete",
                appName,
                "-f"
        ).inheritIO().start();
    }
}
