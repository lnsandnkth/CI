package com.example.database;

/**
 * build information (commit identifier, build date, build logs)
 */
public class BuildInfo {
    private final String commit_id;
    private final String logs;
    private final String build_date;

    public BuildInfo(String commit_id, String logs, String build_date) {
        this.commit_id = commit_id;
        this.logs = logs;
        this.build_date = build_date;
    }

    public String getCommit_id() {
        return this.commit_id;
    }

    public String getLogs() {
        return this.logs;
    }

    public String getBuild_date() {
        return this.build_date;
    }
}
