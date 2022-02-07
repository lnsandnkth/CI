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

    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response)
        throws IOException, ServletException {

        System.out.println(target);
        System.out.println(baseRequest);

        String eventType = baseRequest.getHeader("X-Github-Event");
        if (!eventType.equalsIgnoreCase(PushEvent.TYPE)) {
            System.err.println("[webhook] unsupported event type " + eventType + "! : ");
            response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
            baseRequest.setHandled(true);
            return;
        }

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);

        PushEvent pushEvent = new PushEvent(JsonParser.parseReader(new InputStreamReader(request.getInputStream())));

        // here you do all the continuous integration tasks
        // for example
        // 1st clone your repository
        // 2nd compile the code


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