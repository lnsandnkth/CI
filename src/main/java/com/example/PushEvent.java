package com.example;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

/**
 * Class representing a "push" Github Webhook Event in an HTTP POST Request
 */
public class PushEvent {

    /**
     * Build Status used to set commit statuses on Github
     * Enum constant name must be one of Github's API values in UPPERCASE
     *
     * @link https://docs.github.com/en/rest/reference/commits#commit-statuses Github API documentation for status values
     * @see Commit#postStatus(BuildStatus, Repository) used in this method
     */
    public enum BuildStatus {

        PENDING,
        FAILURE,
        SUCCESS
    }

    /**
     * Value contained in the X-Github-Event header of the "push" Event POST Request
     */
    public static final String TYPE = "push";

    /**
     * Type of Event : always PushEvent.TYPE
     *
     * @see PushEvent#TYPE
     */
    public final String type;

    /**
     * Repository the push has been made on
     *
     * @see Repository
     */
    public final Repository repo;

    /**
     * Github ref value representing the branch pushed on : refs/heads/{PushEvent.branchName}
     *
     * @see PushEvent#branchName
     */
    public final String ref;

    /**
     * Git branch name the push has been made on. Extracted from the Github ref
     *
     * @see PushEvent#ref
     */
    public final String branchName;

    /**
     * The User who made the push
     *
     * @see User
     */
    public final User pusher;

    /**
     * The User that triggered the event (could be Github)
     *
     * @see User
     */
    public final User sender;

    /**
     * Flag : did this push create the ref ?
     *
     * @see PushEvent#ref
     */
    public final boolean created;

    /**
     * Flag : did this push delete de ref ?
     *
     * @see PushEvent#ref
     */
    public final boolean deleted;

    /**
     * Flag : was this a force push (even with lease) ?
     */
    public final boolean forced;

    /**
     * The commit who's the head after the push
     *
     * @see Commit
     */
    public final Commit headCommit;

    /**
     * List of the commits contained in the push
     *
     * @see Commit
     */
    public final List<Commit> commits;

    /**
     * Make a new PushEvent object from a JsonElement retrieved from a "push" Github Webhook Event in an HTTP POST
     * Request
     *
     * @param pushEvent JsonElement representing the push webhook event
     */
    public PushEvent(JsonElement pushEvent) {

        JsonObject eventObj = pushEvent.getAsJsonObject();
        this.type = PushEvent.TYPE;
        this.repo = new Repository(eventObj.getAsJsonObject("repository"));
        this.ref = eventObj.get("ref").getAsString();
        this.branchName = this.ref.split("/")[2];
        this.pusher = new User(eventObj.get("pusher"));
        this.sender = new User(eventObj.get("sender"));
        this.created = eventObj.get("created").getAsBoolean();
        this.deleted = eventObj.get("deleted").getAsBoolean();
        this.forced = eventObj.get("forced").getAsBoolean();

        this.headCommit = new Commit(eventObj.get("head_commit"));
        this.commits = Commit.fromJsonList(eventObj.get("commits"));
    }

    @Override
    public String toString() {

        return "PushEvent{" +
               "type='" + type + '\'' +
               ", repo=" + repo +
               ", ref='" + ref + '\'' +
               ", branchName='" + branchName + '\'' +
               ", pusher=" + pusher +
               ", sender=" + sender +
               ", created=" + created +
               ", deleted=" + deleted +
               ", forced=" + forced +
               ", headCommit=" + headCommit +
               ", commits=" + commits +
               '}';
    }
}
