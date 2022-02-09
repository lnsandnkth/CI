package com.example.database;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class DatabaseHistory{

    public static void generateBuildHistoryPage(HttpServletResponse response) throws IOException {
        // until db stuff is ready this will just be plain text
        String out = "<!DOCTYPE html>" +
                "<html>" +
                "<body>" +
                "<div style=\"max-width: 1000px; margin-left: auto; margin-right: auto; padding-left: 10px; padding-right: 10px;\">\n" +
                "  <h2 style=\"font-size: 26px; margin: 20px 0; text-align: center; small { font-size: 0.5em; }\">Build Info</h2>\n" +
                "  <ul>\n" +
                "    <li style=\"border-radius: 3px; padding: 25px 30px; display: flex; justify-content: space-between; margin-bottom: 25px;background-color: #95A5A6; font-size: 14px; text-transform: uppercase; letter-spacing: 0.03em;\">\n" +
                "      <div style=\"flex-basis: 10%;margin:20px;\">commit identifier</div>\n" +
                "      <div style=\"flex-basis: 10%;margin:20px;\">commit date</div>\n" +
                "      <div style=\"flex-basis: 30%;margin:20px;\">build date</div>\n" +
                "      <div style=\"flex-basis: 50%;margin:20px;\">build logs</div>\n" +
                "    </li>\n" +
                "    <li style=\"border-radius: 3px; padding: 25px 30px; display: flex; justify-content: space-between; margin-bottom: 25px;background-color: #ffffff;box-shadow: 0px 0px 9px 0px rgba(0,0,0,0.1);\">\n" +
                "      <div style=\"flex-basis: 10%;margin:20px;\">#dalkfjgwlk2345</div>\n" +
                "      <div style=\"flex-basis: 10%;margin:20px;\">09.02.2022 19:00</div>\n" +
                "      <div style=\"flex-basis: 30%;margin:20px;\">09.02.2022 19:02</div>\n" +
                "      <div style=\"flex-basis: 50%;margin:20px;\">build was successfull<br>tests were successfull</div>\n" +
                "    </li>\n" +
                "    <li style=\"border-radius: 3px; padding: 25px 30px; display: flex; justify-content: space-between; margin-bottom: 25px;background-color: #ffffff;box-shadow: 0px 0px 9px 0px rgba(0,0,0,0.1);\">\n" +
                "      <div style=\"flex-basis: 10%;margin:20px;\">#dalkfjgwlk2345</div>\n" +
                "      <div style=\"flex-basis: 10%;margin:20px;\">09.02.2022 19:00</div>\n" +
                "      <div style=\"flex-basis: 30%;margin:20px;\">09.02.2022 19:02</div>\n" +
                "      <div style=\"flex-basis: 50%;margin:20px;\">build was successfull<br>tests were successfull</div>\n" +
                "    </li>\n" +
                "    <li style=\"border-radius: 3px; padding: 25px 30px; display: flex; justify-content: space-between; margin-bottom: 25px;background-color: #ffffff;box-shadow: 0px 0px 9px 0px rgba(0,0,0,0.1);\">\n" +
                "      <div style=\"flex-basis: 10%;margin:20px;\">#dalkfjgwlk2345</div>\n" +
                "      <div style=\"flex-basis: 10%;margin:20px;\">09.02.2022 19:00</div>\n" +
                "      <div style=\"flex-basis: 30%;margin:20px;\">09.02.2022 19:02</div>\n" +
                "      <div style=\"flex-basis: 50%;margin:20px;\">build was successfull<br>tests were successfull</div>\n" +
                "    </li>\n" +
                "  </ul>\n" +
                "</div>" +
                "</body>" +
                "</html>";

        response.getWriter().println(out);
    }

    public static void generateBuildInfoPage(HttpServletResponse response) throws IOException {
        // until db stuff is ready this will just be plain text
        String out = "<!DOCTYPE html>" +
            "<html>" +
            "<body>" +
            "<div style=\"max-width: 1000px; margin-left: auto; margin-right: auto; padding-left: 10px; padding-right: 10px;\">\n" +
                "  <h2 style=\"font-size: 26px; margin: 20px 0; text-align: center; small { font-size: 0.5em; }\">Build Info</h2>\n" +
                "  <ul>\n" +
                "    <li style=\"border-radius: 3px; padding: 25px 30px; display: flex; justify-content: space-between; margin-bottom: 25px;background-color: #95A5A6; font-size: 14px; text-transform: uppercase; letter-spacing: 0.03em;\">\n" +
                "      <div style=\"flex-basis: 10%;margin:20px;\">commit identifier</div>\n" +
                "      <div style=\"flex-basis: 10%;margin:20px;\">commit date</div>\n" +
                "      <div style=\"flex-basis: 30%;margin:20px;\">build date</div>\n" +
                "      <div style=\"flex-basis: 50%;margin:20px;\">build logs</div>\n" +
                "    </li>\n" +
                "    <li style=\"border-radius: 3px; padding: 25px 30px; display: flex; justify-content: space-between; margin-bottom: 25px;background-color: #ffffff;box-shadow: 0px 0px 9px 0px rgba(0,0,0,0.1);\">\n" +
                "      <div style=\"flex-basis: 10%;margin:20px;\">#dalkfjgwlk2345</div>\n" +
                "      <div style=\"flex-basis: 10%;margin:20px;\">09.02.2022 19:00</div>\n" +
                "      <div style=\"flex-basis: 30%;margin:20px;\">09.02.2022 19:02</div>\n" +
                "      <div style=\"flex-basis: 50%;margin:20px;\">build was successfull<br>tests were successfull</div>\n" +
                "    </li>\n" +
                "  </ul>\n" +
                "</div>" +
                "</body>" +
                "</html>";

        response.getWriter().println(out);
    }
}