package com.example;

import com.google.gson.JsonParser;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;

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

        System.out.println(target);
        System.out.println(baseRequest);

        // Get event type
        String eventType = baseRequest.getHeader("X-Github-Event");
        if (!eventType.equalsIgnoreCase(PushEvent.TYPE)) { // If not a push event stop
            System.err.println("[webhook] unsupported event type " + eventType + "! : ");
            response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
            baseRequest.setHandled(true);
            return;
        }

        // set response as OK because we know how to deal with 'push' events
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        // Parse 'push' event raw JSON data
        PushEvent pushEvent = new PushEvent(JsonParser.parseReader(new InputStreamReader(request.getInputStream())));

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