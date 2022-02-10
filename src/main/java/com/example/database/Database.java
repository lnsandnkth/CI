package com.example.database;

import java.sql.*;
import java.util.ArrayList;

public class Database {

    private Connection c = null;

    /**
     * Connect to the database
     * @param path specify the path of db
     */
    public void connect(String path) {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + path);
            createTable();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    /**
     * Disconnect to database
     */
    public void disconnect() {
        try{
            if (c != null) {
                c.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Create a new table if not exist
     */
    public void createTable() {
        String sql = """
                CREATE TABLE builds (
                        commit_id TEXT,
                        user_name TEXT,
                        build_date TEXT,
                        build_status INTEGER,
                        test_status INTEGER,
                        logs TEXT
                );
                """;
        try {
            Statement createstat = c.createStatement();
            String checkTable = "SELECT count(name) FROM sqlite_master WHERE type='table' AND name='builds'";
            ResultSet rs = createstat.executeQuery(checkTable);
            if (rs.next())
            {
                int exists = rs.getInt(1);
                if (exists == 0)
                {
                    System.err.println("Added builds table as it did not exist");
                    Statement exestat = c.createStatement();
                    exestat.execute(sql);
                }
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Add a build info to the database
     * @param info BuildInfo, combines the commit identifier and build time from PushEvent.headCommit & logs from OutputStream
     * @return if successful the unique identifier of commit to be used for further refer, otherwise empty string
     */
    public String addInfo(BuildInfo info) {
        String sql = "insert into builds(user_name,commit_id,build_date,build_status,test_status,logs) values(?,?,?,?,?,?)";
        try {
            PreparedStatement prestat = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prestat.setString(1, info.getCommit_id());
            prestat.setString(2, info.getUser_name());
            prestat.setString(3, info.getBuild_date());
            prestat.setInt(4, info.getBuild_status());
            prestat.setInt(5, info.getTest_status());
            prestat.setString(6, info.getLogs());

            if (prestat.executeUpdate() == 1) {
                return info.getCommit_id();
            }
            return "";
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return "";
        }
    }

    /**
     * Return one build info based on the unique key returned when insert it
     * @param commit_id unique identifier of commit used to get the build info
     * @return BuildInfo object includes build information (commit identifier, build date, build logs) if successful get info, otherwise null
     */
    public BuildInfo getOneInfo(String commit_id) {
        String sql = "select * from builds where commit_id = ?";
        try {
            PreparedStatement prestat = c.prepareStatement(sql);
            prestat.setString(1, commit_id);
            ResultSet res = prestat.executeQuery();
            if (res.next()) {
                String user_name = res.getString(2);
                String build_date = res.getString(3);
                int build_status = res.getInt(4);
                int test_status = res.getInt(5);
                String logs = res.getString(6);
                return new BuildInfo(commit_id, user_name, build_date, build_status, test_status, logs);
            }
            return null;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    /**
     * Return a list of all the build information
     * @return ArrayList<BuildInfo> if successful, otherwise null
     */
    public ArrayList<BuildInfo> getAllInfo() {
        String sql = "select * from builds";
        ArrayList<BuildInfo> allBuilds = new ArrayList<>();
        try {
            PreparedStatement prestat = c.prepareStatement(sql);
            ResultSet res = prestat.executeQuery();
            while (res.next()) {
                String commit_id = res.getString(1);
                String user_name = res.getString(2);
                String build_date = res.getString(3);
                int build_status = res.getInt(4);
                int test_status = res.getInt(5);
                String logs = res.getString(6);
                allBuilds.add(new BuildInfo(commit_id, user_name, build_date, build_status, test_status, logs));
            }
            return allBuilds;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}

