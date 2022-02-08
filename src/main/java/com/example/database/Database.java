package com.example.database;

import java.sql.*;

public class Database {
    static Connection c = null;

    public static void connect(String path) {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + path);
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }

    public static void disconnect() {
        try{
            if (c != null) {
                c.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}

