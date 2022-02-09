import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PassingTest {

    @Test
    @DisplayName("Purposely passing test")
    public void Passing() {

        Assertions.assertTrue(true);
    }
}
