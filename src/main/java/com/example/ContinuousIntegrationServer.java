package com.example;

import com.example.database.BuildInfo;
import com.example.database.Database;
import com.example.database.DatabaseHistory;
import com.google.gson.JsonParser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;
import org.gradle.tooling.GradleConnectionException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.DirectoryNotEmptyException;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * Skeleton of a ContinuousIntegrationServer which acts as webhook See the Jetty documentation for API documentation of
 * those classes.
 */
public class ContinuousIntegrationServer extends AbstractHandler {

    public final Database database;

    public ContinuousIntegrationServer(String databaseFile) {

        super();

        this.database = new Database();
        this.database.connect(databaseFile);
    }

    /**
     * Handles an HTTP Request
     *
     * @param target      the HTTP target (e.g. : "/", "/images/...", "/history", etc.)
     * @param baseRequest the "raw" HTTP Request
     * @param request     the processed HTTP Request
     * @param response    the response the Server sends back to the request's sender
     *
     * @throws IOException
     */
    @Override
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response)
        throws IOException {

        System.out.println(target);
        System.out.println(baseRequest);

        switch (request.getRequestURI()) {
            case "/gitevent":
                handlePushEvent(baseRequest, request, response);
                break;
            case "/buildhistory":
                DatabaseHistory.generateBuildHistoryPage(response, database);
                response.setStatus(HttpServletResponse.SC_OK);
                baseRequest.setHandled(true);
                break;
            case "/buildinfo":
                DatabaseHistory.generateBuildInfoPage(response, request, database);
                response.setStatus(HttpServletResponse.SC_OK);
                baseRequest.setHandled(true);
                break;
            default: {
                System.out.println("Request on " + target + " not supported");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                baseRequest.setHandled(true);
            }
        }
    }

    private void handlePushEvent(Request baseRequest,
                                 HttpServletRequest request,
                                 HttpServletResponse response
                                ) throws IOException {

        BiConsumer<String, Integer> onError = (errorMessage, statusCode) -> {

            System.err.println(errorMessage);
            response.setStatus(statusCode);
            baseRequest.setHandled(true);
        };

        BiConsumer<PushEvent, GradleProject> fatalCleanup = (event, project) -> {

            if (project != null) project.close();
            if (event != null) event.repo.clean();
        };

        // Get event type
        String eventType = baseRequest.getHeader("X-Github-Event");
        if (!eventType.equalsIgnoreCase(PushEvent.TYPE)) {
            onError.accept("[webhook] unsupported event type " + eventType + "! : ", HttpServletResponse.SC_NOT_IMPLEMENTED);
            return;
        }

        // set response as OK because we know how to deal with 'push' events
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        // Parse 'push' event raw JSON data
        PushEvent pushEvent = new PushEvent(JsonParser.parseReader(new InputStreamReader(request.getInputStream())));

        System.out.printf("Cloning repository %s...\n", pushEvent.repo.url);
        Git gitRepository = null;
        try {
            gitRepository = pushEvent.repo.cloneRepository(pushEvent.branchName, "./temp_repo/");
        } catch (DirectoryNotEmptyException ex) {
            onError.accept("[webhook] can't clone repository, target directory " + (new File("./temp_repo").getAbsolutePath()) + " not empty!", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        if (gitRepository == null) {
            onError.accept("[webhook] couldn't clone Github Repository! " + pushEvent.repo.fullName, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        // print current state (branch + commit)
        String fullBranch = gitRepository.getRepository().getFullBranch();
        Ref ref = gitRepository.getRepository().exactRef(fullBranch);
        System.out.printf("Checked out branch %s at commit %s...\n", fullBranch, ref.getObjectId().name());

        if (!pushEvent.ref.equals(fullBranch) || !pushEvent.headCommit.id.equals(ref.getObjectId().name())) {
            onError.accept("[webhook] wrong ref / commit checked out! should be on " + pushEvent.ref + " @ " + pushEvent.headCommit.id, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            fatalCleanup.accept(pushEvent, null);
            return;
        }

        System.out.printf("Linking with Gradle Project %s @ %s...\n", pushEvent.repo.fullName, pushEvent.repo.directory());
        GradleProject project = null;
        try {
            project = new GradleProject(pushEvent.repo.directory()).link();
        } catch (GradleConnectionException ex) {
            onError.accept("[webhook] couldn't connect to gradle project in : " + pushEvent.repo.directory() + "\n[webhook][errlog] printing stacktrace...", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ex.printStackTrace();
        }

        if (project == null || !project.linked()) {
            onError.accept("[webhook] project linking failed. must be a valid gradle project!", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            fatalCleanup.accept(pushEvent, project);
            return;
        }

        System.out.println("Linking succeeded !");
        System.out.println("Building project...");

        ByteArrayOutputStream buildLogs = new ByteArrayOutputStream();
        boolean buildStatus = project.buildProject(buildLogs);

        System.out.println("Project " + (buildStatus ? "successfully built" : "build failed") + "!");
        System.out.println("[webhook][build logs]\n" + buildLogs);

        Commit commit = pushEvent.headCommit;
        String newState = commit.postStatus(buildStatus, pushEvent.repo);
        if (newState == null)
            System.out.println("[webhook] Couldn't set status on commit " + commit.id);
        else
            System.out.println("[webhook] Commit " + commit.id + " state set to " + newState + "!");

        // test the project
        boolean testStatus = project.testProject(buildLogs);

        // push the build result to database
        String userNames = pushEvent.commits.stream().map(c -> c.author.name).distinct().collect(Collectors.joining(","));
        String logEntryID = database.addInfo(new BuildInfo(pushEvent.headCommit.id, userNames, pushEvent.headCommit.timestamp, buildStatus ? 1 : 0, testStatus ? 1 : 0, buildLogs.toString()));
        System.out.println("Log entry ID = " + logEntryID);

        // here you do all the continuous integration tasks
        // for example
        // 1st clone your repository
        // 2nd compile the code
        System.out.println("PUSH EVENT :");
        System.out.println(pushEvent);

        response.getWriter().println("CI job done");

        // clean up
        fatalCleanup.accept(pushEvent, project);
    }

    // used to start the CI server in command line
    public static void main(String[] args) throws Exception {


        String portENV = System.getenv("PORT");
        String token = System.getenv("Token");
        if (token != null)
            System.out.println("TOKEN = " + token.subSequence(0, Math.min(token.length(), 5)) + "...");
        else
            System.out.println("TOKEN IS NOT SET");

        System.out.println("ENV PORT IS " + portENV);

        // get PORT from the environment variables
        int port;
        try {
            port = Integer.parseInt(portENV);
        } catch (NumberFormatException ex) {
            port = 8080;
            System.out.println("NO ENV SET DEFAULTING TO " + port);
        }
        System.out.println("SELECTED PORT IS " + port);

        // connect to the database

        Server server = new Server(port);
        server.setHandler(new ContinuousIntegrationServer("history.db"));
        server.start();
        server.join();
    }
}