package com.example.database;

/**
 * build information (commit identifier, build date, build logs)
 */
public class BuildInfo {
    private final String commit_id;
    private final String logs;
    private final String build_date;

    /**
     * BuildInfo constructor
     * @param commit_id The SHA1 id of the commit
     * @param logs Logs
     * @param build_date Date of the build
     */
    public BuildInfo(String commit_id, String logs, String build_date) {
        this.commit_id = commit_id;
        this.logs = logs;
        this.build_date = build_date;
    }

    /**
     * Get the commit_id
     * @return commit_id of this instance
     */
    public String getCommit_id() {
        return this.commit_id;
    }

    /**
     * Get the logs
     * @return the logs of this instance
     */
    public String getLogs() {
        return this.logs;
    }

    /**
     * Get the build date
     * @return the build date of this instance
     */
    public String getBuild_date() {
        return this.build_date;
    }
}
