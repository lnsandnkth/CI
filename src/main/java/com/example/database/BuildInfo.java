package com.example.database;

/**
 * build information (commit identifier, build date, build logs)
 */
public class BuildInfo {

    private final String commit_id;
    private final String user_name;
    private final String build_date;
    private final int build_status; // 1: pass, 0: fail
    private final int test_status; // 1: pass, 0: fail
    private final String logs;

    /**
     * BuildInfo constructor
     * @param commit_id The SHA1 id of the commit
     * @param user_name The user who commited
     * @param build_date Date of the build
     * @param build_status 0 if build failed, 1 if successfull
     * @param test_status 0 if tests failed, 1 if successfull
     * @param logs Logs
     */
    public BuildInfo(String commit_id, String user_name, String build_date, int build_status, int test_status, String logs) {
        this.commit_id = commit_id;
        this.user_name = user_name;
        this.build_date = build_date;
        this.build_status = build_status;
        this.test_status = test_status;
        this.logs = logs;
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

    /**
     * @return 1 if pass, 0 if fail
     */
    public Integer getBuild_status() {

        return build_status;
    }

    /**
     * @return one string consists of all the usernames, separated by ','
     */
    public String getUser_name() {

        return user_name;
    }

    /**
     * @return 1 if pass, 0 if fail
     */
    public Integer getTest_status() {

        return test_status;
    }
}
