import com.example.GradleProject;
import jdk.jfr.Description;
import org.gradle.tooling.GradleConnectionException;
import org.gradle.tooling.model.GradleTask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class GradleToolingTest {

    @Test
    @DisplayName("Try to link Gradle Tooling to a valid gradle project")
    public void LinkProjectPass() {

        String wd = "src/test/resources/CI_build_pass";
        File wdf = new File(wd);

        String[] files = wdf.list();
        if (files != null)
            System.out.println("Working dir content : \n" + String.join("\n", files));

        Assertions.assertDoesNotThrow(
            () -> {
                Assertions.assertTrue(new GradleProject(wdf).link().linked());
            }
                                     );
    }

    @Test
    @DisplayName("Try to link Gradle Tooling to a not-a-gradle-project directory")
    public void LinkProjectFail() {

        String wd = "src/test/resources/";
        File wdf = new File(wd);

        String[] files = wdf.list();
        if (files != null)
            System.out.println("Working dir content : \n" + String.join("\n", files));

        GradleProject project = new GradleProject(wdf);
        Assertions.assertAll(
            () -> Assertions.assertThrows(GradleConnectionException.class, project::link),
            () -> Assertions.assertFalse(project.linked())
                            );
    }

    @Test
    @DisplayName("Try to build a valid gradle project")
    public void BuildPass() {

        String wd = "src/test/resources/CI_build_pass";
        File wdf = new File(wd);

        String[] files = wdf.list();
        if (files != null)
            System.out.println("Working dir content : \n" + String.join("\n", files));

        ByteArrayOutputStream logs = new ByteArrayOutputStream();
        boolean shouldBeTrue = new GradleProject(wdf).link().buildProject(logs);

        System.out.println("Gradle Build Logs : \n" + logs);

        Assertions.assertTrue(shouldBeTrue);
    }

    @Test
    @DisplayName("Close a valid gradle project")
    @Description("No negative test for this one because it can't fail")
    public void ClosePass() {

        String wd = "src/test/resources/CI_build_pass";
        File wdf = new File(wd);

        String[] files = wdf.list();
        if (files != null)
            System.out.println("Working dir content : \n" + String.join("\n", files));

        ByteArrayOutputStream logs = new ByteArrayOutputStream();
        GradleProject project = new GradleProject(wdf).link();

        System.out.println("Gradle Build Logs : \n" + logs);

        Assertions.assertTrue(project.linked());

        project.close();

        Assertions.assertFalse(project.linked());
    }

    @Test
    @DisplayName("Try to build a invalid gradle project")
    public void BuildFail() {

        String wd = "src/test/resources/CI_build_fail";
        File wdf = new File(wd);

        String[] files = wdf.list();
        if (files != null)
            System.out.println("Working dir content : \n" + String.join("\n", files));

        ByteArrayOutputStream logs = new ByteArrayOutputStream();
        boolean shouldBeFalse = new GradleProject(wdf).link().buildProject(logs);

        System.out.println("Gradle Build Logs : \n" + logs);

        Assertions.assertFalse(shouldBeFalse);
    }

    @Test
    @DisplayName("Try to list all tests tasks from gradle project")
    public void ListTestTasks() {

        String wd = "src/test/resources/CI_test_pass";
        File wdf = new File(wd);

        String[] files = wdf.list();
        if (files != null)
            System.out.println("Working dir content : \n" + String.join("\n", files));

        GradleProject project = new GradleProject(wd).link();
        List<GradleTask> testTasks = project.listTasks().stream().filter(task -> task.isPublic() && task.getName().contains("test")).collect(Collectors.toList());

        for (GradleTask task : testTasks) {
            System.out.println("===");
            System.out.println(task);
            System.out.println(task.getPath());
            System.out.println(task.getName());
            System.out.println(task.getDisplayName());
            System.out.println(task.getDescription());
            System.out.println(task.getGroup());
        }

        Assertions.assertEquals(2, testTasks.size());
    }

    @Test
    @DisplayName("Try to test a valid gradle project with all-pass tests")
    public void TestPass() {

        String wd = "src/test/resources/CI_test_pass";
        File wdf = new File(wd);

        String[] files = wdf.list();
        if (files != null)
            System.out.println("Working dir content : \n" + String.join("\n", files));

        ByteArrayOutputStream logs = new ByteArrayOutputStream();
        boolean shouldBeTrue = new GradleProject(wd).link().testProject(logs);

        System.out.println("TESTS LOGS : \n" + logs);

        Assertions.assertTrue(shouldBeTrue);
    }

    @Test
    @DisplayName("Try to test a valid gradle project without tests")
    public void NoTestPass() {

        String wd = "src/test/resources/CI_test_notest";
        File wdf = new File(wd);

        String[] files = wdf.list();
        if (files != null)
            System.out.println("Working dir content : \n" + String.join("\n", files));

        ByteArrayOutputStream logs = new ByteArrayOutputStream();
        boolean shouldBeTrue = new GradleProject(wd).link().testProject(logs);

        System.out.println("TESTS LOGS : \n" + logs);

        Assertions.assertTrue(shouldBeTrue);
    }

    @Test
    @DisplayName("Try to test a valid gradle project with at least one failing test")
    public void TestFail() {

        String wd = "src/test/resources/CI_test_fail";
        File wdf = new File(wd);

        String[] files = wdf.list();
        if (files != null)
            System.out.println("Working dir content : \n" + String.join("\n", files));

        ByteArrayOutputStream logs = new ByteArrayOutputStream();
        boolean shouldBeFalse = new GradleProject(wd).link().testProject(logs);

        System.out.println("TESTS LOGS : \n" + logs);

        Assertions.assertFalse(shouldBeFalse);
    }
}
