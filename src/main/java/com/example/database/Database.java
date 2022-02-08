package com.example.database;

import java.sql.*;

public class Database {
    static Connection c = null;

    /**
     * connect to the database
     * @param path specify the path of db
     */
    public static void connect(String path) {
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
     * disconnect to database
     */
    public static void disconnect() {
        try{
            if (c != null) {
                c.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * create a new table if not exist
     */
    public static void createTable() {
        String sql = """
                CREATE TABLE builds (
                        uid INTEGER PRIMARY KEY,
                        commit_hash TEXT,
                        content TEXT,
                        timestamp TIMESTAMP
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
    //TODO the input data type should be specified as issue#20 / 22 ?
    public void addInfo() {

    }


}

