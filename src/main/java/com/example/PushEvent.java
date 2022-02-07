package com.example;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class PushEvent {

    public static String TYPE = "push";

    public String type;
    public Repository repo;
    public String ref, branchName;

    public PushEvent(JsonElement pushEvent) {

        JsonObject eventObj = pushEvent.getAsJsonObject();
        this.type = PushEvent.TYPE;
        this.repo = new Repository(eventObj.getAsJsonObject("repository"));
        this.ref = eventObj.get("ref").getAsString();
        this.branchName = this.ref.split("/")[2];
    }

    @Override
    public String toString() {

        return "PushEvent{" +
               "type='" + type + '\'' +
               ", repo=" + repo +
               ", ref='" + ref + '\'' +
               ", branchName='" + branchName + '\'' +
               '}';
    }

}
