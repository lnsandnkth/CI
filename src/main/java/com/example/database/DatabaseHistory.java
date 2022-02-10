package com.example.database;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DatabaseHistory{

    public static void generateBuildHistoryPage(HttpServletResponse response, Database database) throws IOException {
        ArrayList<BuildInfo> bis = database.getAllInfo();

        String out = """
                <!DOCTYPE html>
                <html>
                <body>
                <div style=\"max-width: 1000px; margin-left: auto; margin-right: auto; padding-left: 10px; padding-right: 10px;\">
                  <h2 style=\"font-size: 26px; margin: 20px 0; text-align: center; small { font-size: 0.5em; }\">Build Info</h2>
                  <ul>
                     <li style=\"border-radius: 3px; padding: 25px 30px; display: flex; justify-content: space-between; margin-bottom: 25px;background-color: #95A5A6; font-size: 14px; text-transform: uppercase; letter-spacing: 0.03em;\">
                       <div style=\"flex-basis: 20%;margin:20px;\">commit identifier</div>
                       <div style=\"flex-basis: 10%;margin:20px;\">build date</div>
                       <div style=\"flex-basis: 70%;margin:20px;\">build logs</div>
                     </li>
                     """;
                     for(BuildInfo bi : bis){
                        out += """
                                <li style=\"border-radius: 3px; padding: 25px 30px; display: flex; justify-content: space-between; margin-bottom: 25px;background-color: #ffffff;box-shadow: 0px 0px 9px 0px rgba(0,0,0,0.1);\">
                                  <div style=\"flex-basis: 20%;margin:20px;\">
                                      """;
                        out += bi.getCommit_id();
                        out += """
                                </div>
                                <div style=\"flex-basis: 10%;margin:20px;\">
                                    """;
                        out += bi.getBuild_date();
                        out += """
                                </div>
                                <div style=\"flex-basis: 70%;margin:20px;\">
                                    """;
                        out += bi.getLogs();
                        out += """
                                  </div>
                                </li>
                                """;
                     }
                     out+="""
                  </ul>
                </div>
                </body>
                </html>""";

        response.getWriter().println(out);
    }

    public static void generateBuildInfoPage(HttpServletResponse response, HttpServletRequest request, Database database) throws IOException {
        String commit_id = request.getParameter("commit_id");
        BuildInfo bi = database.getOneInfo(commit_id);
        if(bi == null){
            response.getWriter().println("Wrong or no commit_id given in post parameter");
            return;
        }

        String out = """
            <!DOCTYPE html>
           <html>
           <body>
           <div style=\"max-width: 1000px; margin-left: auto; margin-right: auto; padding-left: 10px; padding-right: 10px;\">
                 <h2 style=\"font-size: 26px; margin: 20px 0; text-align: center; small { font-size: 0.5em; }\">Build Info</h2>
                 <ul>
                   <li style=\"border-radius: 3px; padding: 25px 30px; display: flex; justify-content: space-between; margin-bottom: 25px;background-color: #95A5A6; font-size: 14px; text-transform: uppercase; letter-spacing: 0.03em;\">
                     <div style=\"flex-basis: 20%;margin:20px;\">commit identifier</div>
                     <div style=\"flex-basis: 10%;margin:20px;\">build date</div>
                     <div style=\"flex-basis: 70%;margin:20px;\">build logs</div>
                   </li>
                   <li style=\"border-radius: 3px; padding: 25px 30px; display: flex; justify-content: space-between; margin-bottom: 25px;background-color: #ffffff;box-shadow: 0px 0px 9px 0px rgba(0,0,0,0.1);\">
                     <div style=\"flex-basis: 20%;margin:20px;\">
                         """;
                         out+=bi.getCommit_id();
                         out+="""
                     </div>
                     <div style=\"flex-basis: 10%;margin:20px;\">
                         """;
                         out+=bi.getBuild_date();
                         out+= """
                     </div>
                     <div style=\"flex-basis: 70%;margin:20px;\">
                         """;
                         out+=bi.getLogs();
                         out+="""
                     </div>
                   </li>
                 </ul>
               </div>
               </body>
               </html>""";

        response.getWriter().println(out);
    }
}