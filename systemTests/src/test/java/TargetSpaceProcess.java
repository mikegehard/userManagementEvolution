import java.io.IOException;

public class TargetSpaceProcess {
    public Process run(String space) throws IOException {
        return new ProcessBuilder(
                "cf",
                "target",
                "-s",
                space
        ).inheritIO().start();
    }
}
