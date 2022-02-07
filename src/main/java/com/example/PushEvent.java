package com.example;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;

public class PushEvent {

    public static final String TYPE = "push";

    public final String type;
    public final Repository repo;
    public final String ref, branchName;
    public final User pusher, sender;
    public final boolean created, deleted, forced;

    public final Commit headCommit;
    public final List<Commit> commits;

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
