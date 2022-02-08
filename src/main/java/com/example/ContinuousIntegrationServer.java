package com.example;

import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Skeleton of a ContinuousIntegrationServer which acts as webhook See the Jetty documentation for API documentation of
 * those classes.
 */
public class ContinuousIntegrationServer extends AbstractHandler {

    /**
     * Handles an HTTP Request
     *
     * @param target the HTTP target (e.g. : "/", "/images/...", "/history", etc.)
     * @param baseRequest the "raw" HTTP Request
     * @param request the processed HTTP Request
     * @param response the response the Server sends back to the request's sender
     *
     * @throws IOException
     */
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response)
        throws IOException {

        BiConsumer<String, Integer> onError = (errorMessage, statusCode) -> {

            System.err.println(errorMessage);
            response.setStatus(statusCode);
            baseRequest.setHandled(true);
        };

        System.out.println(target);
        System.out.println(baseRequest);

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
        Git gitRepository = pushEvent.repo.cloneRepository(pushEvent.branchName, "./temp_repo/");

        // dir used for repo cloning
        if (gitRepository == null) {
            onError.accept("[webhook] couldn't clone Github Repository! " + pushEvent.repo, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        // print current state (branch + commit)
        String fullBranch = gitRepository.getRepository().getFullBranch();
        Ref ref = gitRepository.getRepository().exactRef(fullBranch);
        System.out.printf("Checked out branch %s at commit %s...\n", fullBranch, ref.getObjectId().name());

        // clean up repository
        pushEvent.repo.clean();

        // here you do all the continuous integration tasks
        // for example
        // 1st clone your repository
        // 2nd compile the code
        System.out.println("PUSH EVENT :");
        System.out.println(pushEvent);

        response.getWriter().println("CI job done");
    }

    // used to start the CI server in command line
    public static void main(String[] args) throws Exception {


        String portENV = System.getenv("PORT");
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

        Server server = new Server(port);
        server.setHandler(new ContinuousIntegrationServer());
        server.start();
        server.join();
    }
}