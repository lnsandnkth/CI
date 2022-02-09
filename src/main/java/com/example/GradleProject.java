package com.example;

import org.gradle.tooling.*;
import org.gradle.tooling.model.GradleTask;

import java.io.File;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

public class GradleProject {

    private final File wdf;
    private GradleConnector connector;
    private ProjectConnection connection;

    public GradleProject(String projectRootPath) {

        if (projectRootPath == null)
            throw new IllegalArgumentException("projectRootPath can't be null");

        File wdf = new File(projectRootPath);
        if (!wdf.exists() || !wdf.isDirectory()) {
            throw new IllegalArgumentException("projectRootPath must point to an existing directory");
        }

        this.wdf = wdf;
    }

    public GradleProject(File projectRootDirectory) {

        if (projectRootDirectory == null)
            throw new IllegalArgumentException("projectRootDirectory can't be null");

        if (!projectRootDirectory.exists() || !projectRootDirectory.isDirectory()) {
            throw new IllegalArgumentException("projectRootDirectory must point to an existing directory");
        }

        this.wdf = projectRootDirectory;
    }

    public GradleProject link() throws GradleConnectionException {

        this.connector = GradleConnector.newConnector().forProjectDirectory(this.wdf);
        this.connection = this.connector.connect();

        try {
            // try to run something from the project to check if it's working
            this.connection.getModel(org.gradle.tooling.model.GradleProject.class);
        } catch (Exception ex) {

            this.close();
            throw ex;
        }

        return this;
    }

    public boolean linked() {

        return connector != null && connection != null;
    }

    public void close() {

        this.connection.close();
        this.connector.disconnect();

        this.connector = null;
        this.connection = null;
    }

    public boolean buildProject(OutputStream logs) throws IllegalStateException {

        try {
            this.runTasks(
                logs,
                this.listTasks().stream()
                    .filter(task -> task.isPublic() && task.getName().equalsIgnoreCase("build"))
                    .findFirst()
                    .orElse(null));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public boolean testProject(OutputStream logs) throws IllegalStateException, GradleConnectionException {

        GradleTask[] testTasks = this.listTasks().stream()
                                     .filter(task -> task.isPublic() && task.getName().contains("test"))
                                     .collect(Collectors.toList())
                                     .toArray(GradleTask[]::new);

        try {
            return this.runTasks(logs, testTasks);
        } catch (TestExecutionException testExecutionException) {
            return testExecutionException.getMessage().equalsIgnoreCase("no test declared for execution.");
        } catch (Exception exception) {
            return false;
        }
    }

    public boolean runTasks(OutputStream logs, GradleTask... tasks) throws IllegalStateException, GradleConnectionException {

        if (!this.linked()) {
            throw new IllegalStateException("The GradleProject must be linked using GradleProject#link() before being used.");
        }
        if (tasks == null || tasks.length == 0)
            return false;

        BuildLauncher launcher = this.connection.newBuild().forTasks(tasks);

        if (logs != null)
            launcher.setStandardOutput(logs);

        launcher.run();

        return true;
    }

    public List<? extends GradleTask> listTasks() throws IllegalStateException, GradleConnectionException {

        if (!this.linked()) {
            throw new IllegalStateException("The GradleProject must be linked using GradleProject#link() before being used.");
        }

        return this.connection.getModel(org.gradle.tooling.model.GradleProject.class).getTasks().getAll();
    }
}
