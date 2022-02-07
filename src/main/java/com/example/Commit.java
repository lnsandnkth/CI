package com.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Commit {

    public final String
        id,
        message,
        timestamp,
        url;

    public final User author;

    public final String[] modified;

    public Commit(JsonElement commitElement) {

        JsonObject commitObj = commitElement.getAsJsonObject();
        this.id = commitObj.get("id").getAsString();
        this.message = commitObj.get("message").getAsString();
        this.timestamp = commitObj.get("timestamp").getAsString();
        this.url = commitObj.get("url").getAsString();
        this.author = new User(commitObj.get("author"));

        JsonArray modified = commitObj.getAsJsonArray("modified");
        this.modified = new String[modified.size()];
        for (int i = 0; i < modified.size(); i++) {
            this.modified[i] = modified.get(i).getAsString();
        }
    }

    @Override
    public String toString() {

        return "Commit{" +
               "id='" + id + '\'' +
               ", message='" + message + '\'' +
               ", timestamp='" + timestamp + '\'' +
               ", url='" + url + '\'' +
               ", author=" + author +
               ", modified=" + Arrays.toString(modified) +
               '}';
    }

    public static List<Commit> fromJsonList(JsonElement commits) {

        ArrayList<Commit> result = new ArrayList<>();

        JsonArray commitArray = commits.getAsJsonArray();
        for (JsonElement el : commitArray) {
            result.add(new Commit(el));
        }

        return result;
    }
}
