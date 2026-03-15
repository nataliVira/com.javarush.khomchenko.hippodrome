import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Disabled("Временно отключен")
    @Timeout(value = 22, unit = TimeUnit.SECONDS)
    @Test
    void failsIfExecutionTimeExceeds22Seconds() throws Exception {
        Main.main(new String[0]);
    }
}