package com.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class representing a Git commit in a PushEvent
 *
 * @see PushEvent
 */
public class Commit {

    /**
     * The id of the Commit, the SHA-something commit hash that uniquely identifies the commits in the Git repo
     */
    public final String id;

    /**
     * The commit message written by the author
     */
    public final String message;

    /**
     * The raw timestamp as given by the Github API (String)
     */
    public final String timestamp;

    /**
     * The URL to the commit summary on Github
     */
    public final String url;

    /**
     * The commit's author as a User object
     *
     * @see User
     */
    public final User author;

    /**
     * Finite sized array of filenames/paths modified by the commit
     */
    public final String[] modified;

    /**
     * Make a new Commit from a JsonElement representing a Git commit. This JsonElement must be retrieved from the
     * Github API's Push Event
     *
     * @param commitElement JsonElement representing the commit
     *
     * @see PushEvent
     */
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

    /**
     * Get a list of Commit objects from a JSON Array containing JSON Objects representing Git commits. This JSON Array
     * and contained JSON Objects must have been retrieved from Github's Webhook API
     *
     * @param commits JsonArray containing the commits as a JsonElement
     *
     * @return List of the commits contained in the list, wrapped in Commit objects
     *
     * @see Commit
     */
    public static List<Commit> fromJsonList(JsonElement commits) {

        ArrayList<Commit> result = new ArrayList<>();

        JsonArray commitArray = commits.getAsJsonArray();
        for (JsonElement el : commitArray) {
            result.add(new Commit(el));
        }

        return result;
    }
}
