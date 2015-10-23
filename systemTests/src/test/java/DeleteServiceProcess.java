import java.io.IOException;

public class DeleteServiceProcess {
    public Process run(String serviceName) throws IOException {
        return new ProcessBuilder(
                "cf",
                "delete-service",
                serviceName,
                "-f"
        ).inheritIO().start();
    }
}
