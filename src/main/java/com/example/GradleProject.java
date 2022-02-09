package com.example;

import org.gradle.tooling.*;
import org.gradle.tooling.model.GradleTask;

import java.io.File;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a Gradle Project connected using the Gradle Tooling API dependency Can be used to run tests on an external
 * gradle project, build it, list the tasks and/or run any task from it
 */
public class GradleProject {

    /**
     * Working Directory File : a File pointing to the root directory of the Gradle project
     *
     * @see File
     */
    private final File wdf;

    /**
     * Gradle Connector used to obtain a connection to the Gradle project
     *
     * @see GradleConnector
     */
    private GradleConnector connector;

    /**
     * Project Connection used to run and list tasks on the project
     *
     * @see ProjectConnection
     */
    private ProjectConnection connection;

    /**
     * Make a GradleProject from the path of the project's root directory
     *
     * @param projectRootPath file path to the root directory of the gradle project
     *
     * @throws IllegalArgumentException when the path doesn't exist or is not a directory
     */
    public GradleProject(String projectRootPath) throws IllegalArgumentException {

        this(new File(projectRootPath));
    }

    /**
     * Make a GradleProject from the file of the project's root directory
     *
     * @param projectRootDirectory File object to the root directory of the gradle project
     *
     * @throws IllegalArgumentException when the file doesn't exist or is not a directory
     */
    public GradleProject(File projectRootDirectory) throws IllegalArgumentException {

        if (projectRootDirectory == null)
            throw new IllegalArgumentException("project root directory can't be null");

        if (!projectRootDirectory.exists() || !projectRootDirectory.isDirectory()) {
            throw new IllegalArgumentException("project root directory must point to an existing directory");
        }

        this.wdf = projectRootDirectory;
    }

    /**
     * Connects to the project and tries to get the model to ensure connection has been established
     *
     * @return this
     *
     * @throws GradleConnectionException if the directory doesn't point to a valid Gradle Project
     */
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

    /**
     * Returns the (assumed) connection state of the project
     *
     * @return true if the project has been successfully linked
     */
    public boolean linked() {

        return connector != null && connection != null;
    }

    /**
     * Closes the connection to the project and cleans up memory
     */
    public void close() {

        this.connection.close();
        this.connector.disconnect();

        this.connector = null;
        this.connection = null;
    }

    /**
     * Tries to run the 'build' task on the Gradle project. First lists the available tasks, picks the first one called
     * 'build' and tries to run it
     *
     * @param logs output stream to redirect the logs to (use ByteArrayOutputStream to get a String)
     *
     * @return true if the build has been (seemingly) successful
     *
     * @throws IllegalStateException if the project has not been linked yet
     * @see GradleProject#link() to link the project before using this
     * @see GradleProject#listTasks() used to listing the available tasks
     * @see GradleProject#runTasks(OutputStream, GradleTask...) used to run the 'build' task
     * @see java.io.ByteArrayOutputStream for String logs output
     */
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

    /**
     * Tries to run all the tasks containing 'test' in their name on the Gradle project. First lists the available
     * tasks, filters out the tasks without 'test' in the name, and run all of them. Checks whether the Exception
     * message looks like a "no tests are defined on the project" message when a TestExecutionException is caught.
     *
     * @param logs output stream to redirect the logs to (use ByteArrayOutputStream to get a String)
     *
     * @return true if the tests tasks have been (seemingly) successful or if none were found
     *
     * @throws IllegalStateException if the project has not been linked yet
     * @see GradleProject#link() to link the project before using this
     * @see GradleProject#listTasks() used to listing the available tasks
     * @see GradleProject#runTasks(OutputStream, GradleTask...) used to run the testing tasks
     * @see java.io.ByteArrayOutputStream for String logs output
     */
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

    /**
     * Tries to run all the tasks containing given in the vararg parameter
     *
     * @param logs  output stream to redirect the logs to (use ByteArrayOutputStream to get a String)
     * @param tasks tasks to runs
     *
     * @return true if the tasks have been (seemingly) successful
     *
     * @throws IllegalStateException if the project has not been linked yet
     * @see GradleProject#link() to link the project before using this
     * @see GradleProject#listTasks() to find tasks on a project
     * @see org.gradle.tooling.model.GradleProject#getTasks() to find tasks on a project
     * @see GradleTask
     * @see java.io.ByteArrayOutputStream for String logs output
     */
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

    /**
     * Lists all the tasks defined in the gradle project.
     *
     * @return list containing all the defined tasks on the project
     *
     * @throws IllegalStateException if the project has not been linked yet
     * @see GradleProject#link() to link the project before using this
     * @see org.gradle.tooling.model.GradleProject#getTasks() to find tasks on a project
     * @see GradleTask
     */
    public List<? extends GradleTask> listTasks() throws IllegalStateException, GradleConnectionException {

        if (!this.linked()) {
            throw new IllegalStateException("The GradleProject must be linked using GradleProject#link() before being used.");
        }

        return this.connection.getModel(org.gradle.tooling.model.GradleProject.class).getTasks().getAll();
    }
}
