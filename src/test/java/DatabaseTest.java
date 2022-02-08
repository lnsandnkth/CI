import com.example.database.Database;

public class DatabaseTest {
    static void setup() {
        Database db = new Database();
        Database.connect();
    }
}