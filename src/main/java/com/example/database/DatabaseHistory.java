package com.example.database;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Class for handling
 */
public class DatabaseHistory{
    /**
     * Method for building an HTML page of build histories.
     * @param response HTTP response
     * @param database Database to retrieve data from
     * @throws IOException if error in input data
     */
    public static void generateBuildHistoryPage(HttpServletResponse response, Database database) throws IOException {
        ArrayList<BuildInfo> bis = database.getAllInfo();

        if(bis == null){
            response.getWriter().println("The database is corrupted");
            return;
        }

        String out = """
                <!DOCTYPE html>
                <html>
                <body>
                <div style="max-width: 1000px; margin-left: auto; margin-right: auto; padding-left: 10px; padding-right: 10px;">
                  <h2 style="font-size: 26px; margin: 20px 0; text-align: center; small { font-size: 0.5em; }">Build Info</h2>
                  <ul>
                     <li style="border-radius: 3px; padding: 25px 0; display: flex; justify-content: space-between; margin-bottom: 25px;background-color: #95A5A6; font-size: 14px; text-transform: uppercase; letter-spacing: 0.03em;">
                       <div style="width:10%;margin:10px;text-align:center;">commit identifier</div>
                       <div style="width:10%;margin:10px;text-align:center;">user name</div>
                       <div style="width:10%;margin:10px;text-align:center;">build status</div>
                       <div style="width:10%;margin:10px;text-align:center;">test status</div>
                       <div style="width:20%;margin:10px;text-align:center;">build date</div>
                       <div style="width:40%;margin:10px;text-align:center;">build logs</div>
                     </li>
                     """;
                     for(BuildInfo bi : bis){
                        out += """
                                <li style="border-radius: 3px; padding: 25px 0; display: flex; justify-content: space-between; margin-bottom: 25px;background-color: #ffffff;box-shadow: 0px 0px 9px 0px rgba(0,0,0,0.1);">
                                <div style="width:10%;margin:10px;text-align:center;">
                                  <a href="/buildinfo?commit_id=""" + bi.getCommit_id() + "\">";
                        out += bi.getCommit_id().subSequence(0, Math.min(bi.getCommit_id().length(), 8));
                        out += """
                                  </a>
                                </div>
                                <div style="width:10%;margin:10px;text-align:center;">
                                    """;
                        out += bi.getUser_name();
                        out += """
                                </div>
                                <div style="width:10%;margin:10px;text-align:center;">
                                    """;
                        out += bi.getBuild_status() == 0 ? "Fail":"Success";
                        out += """
                                </div>
                                <div style="width:10%;margin:10px;text-align:center;">
                                    """;
                        out += bi.getTest_status() == 0 ? "Fail":"Success";
                        out += """
                                </div>
                                <div style="width:20%;margin:10px;text-align:center;">
                                    """;
                        out += bi.getBuild_date();
                        out += """
                                </div>
                                <div style="width:40%;margin:10px;overflow-wrap:anywhere;">
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

    /**
     * Method for generating HTML page for build info
     * @param response HTTP response
     * @param request HTTP request
     * @param database database to get build data from
     * @throws IOException if error in input data
     */
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
                <div style="max-width: 1000px; margin-left: auto; margin-right: auto; padding-left: 10px; padding-right: 10px;">
                      <h2 style="font-size: 26px; margin: 20px 0; text-align: center; small { font-size: 0.5em; }">Build Info</h2>
                      <ul>
                        <li style="border-radius: 3px; padding: 25px 0px; display: flex; justify-content: space-between; margin-bottom: 25px;background-color: #95A5A6; font-size: 14px; text-transform: uppercase; letter-spacing: 0.03em;">
                           <div style="width:10%;margin:10px;text-align:center;">commit identifier</div>
                           <div style="width:10%;margin:10px;text-align:center;">user name</div>
                           <div style="width:10%;margin:10px;text-align:center;">build status</div>
                           <div style="width:10%;margin:10px;text-align:center;">test status</div>
                           <div style="width:20%;margin:10px;text-align:center;">build date</div>
                           <div style="width:40%;margin:10px;text-align:center;">build logs</div>
                        </li>
                        <li style="border-radius: 3px; padding: 25px 0px; display: flex; justify-content: space-between; margin-bottom: 25px;background-color: #ffffff;box-shadow: 0px 0px 9px 0px rgba(0,0,0,0.1);">
                                  <div style="width:10%;margin:10px;text-align:center;overflow-wrap: anywhere;">""";
                          out += bi.getCommit_id();
                          out += """
                                    </a>
                                  </div>
                                  <div style="width:10%;margin:10px;text-align:center;">
                                      """;
                          out += bi.getUser_name();
                          out += """
                                  </div>
                                  <div style="width:10%;margin:10px;text-align:center;">
                                      """;
                          out += bi.getBuild_status() == 0 ? "Fail":"Success";
                          out += """
                                  </div>
                                  <div style="width:10%;margin:10px;text-align:center;">
                                      """;
                          out += bi.getTest_status() == 0 ? "Fail":"Success";
                          out += """
                                  </div>
                                  <div style="width:20%;margin:10px;text-align:center;">
                                      """;
                          out += bi.getBuild_date();
                          out += """
                                  </div>
                                  <div style="width:40%;margin:10px;overflow-wrap:anywhere;">
                                      """;
                          out += bi.getLogs();
                          out += """
                                    </div>
                        </li>
                      </ul>
                    </div>
                    </body>
                    </html>""";

        response.getWriter().println(out);
    }
}