import com.example.database.Database;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class DatabaseTest {
    @BeforeAll
    static void setup() {
        Database.connect("test.db");
    }

}