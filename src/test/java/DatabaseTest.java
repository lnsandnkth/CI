import com.example.database.BuildInfo;
import com.example.database.Database;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class DatabaseTest {
    static Database database;

    static ArrayList<BuildInfo> infos;

    @BeforeAll
    @DisplayName("Setup a separate database used for testing")
    static void setup() {
        database.connect("test.db");
    }

    @Test
    @DisplayName("Test adding entries to database")
    static void testAddInfo() {
        String commit = "4f5j6fu9o";
        String log = "This is a test log ";
        for (int i = 0; i < 5; i++) {
            String currentTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
            String newCommitId = commit.concat(String.valueOf(i));
            String newLog = log.concat(String.valueOf(i));
            int build_status = 1;
            int test_status = 1;
            BuildInfo info = new BuildInfo(newCommitId, "user_name_test", currentTime, build_status, test_status, newLog);
            infos.add(info);
            String returnCommitId = database.addInfo(info);
            Assertions.assertEquals(newCommitId, returnCommitId);

        }
    }

    @Test
    @DisplayName("Test getting one entry from the database")
    static void testGetOneInfo() {

        String commitId = infos.get(0).getCommit_id();
        BuildInfo returnInfo = database.getOneInfo(commitId);
        Assertions.assertEquals(infos.get(0), returnInfo);
    }

    @Test
    @DisplayName("Test getting all entries from the database")
    static void testGetAllInfo() {
        Assertions.assertEquals(infos, database.getAllInfo());
    }


}
