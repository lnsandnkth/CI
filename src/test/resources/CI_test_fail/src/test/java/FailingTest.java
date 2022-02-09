import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FailingTest {

    @Test
    @DisplayName("Purposely failing test")
    public void Failing() {

        Assertions.fail();
    }
}
