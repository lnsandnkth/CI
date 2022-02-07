package com.example;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * A class representing a Github User It could be the Repository owner, the commit's author, the committer, the pusher,
 * the event sender, etc.
 *
 * @see PushEvent
 * @see Repository
 * @see Commit
 */
public class User {

    /**
     * The user's name : is either the user's login, username or display name (in this order of data disponibility)
     */
    public final String name;

    /**
     * The user's email : user email as a String if available or empty String "" if not available
     */
    public final String email;

    /**
     * A link to the user's Github profile page : user profile URL as String if available or empty String "" if not
     */
    public final String htmlUrl;

    /**
     * A link to the user's Github avatar image : user avatar image URL as String if available or empty String "" if
     * not
     */
    public final String avatarUrl;

    /**
     * Make a new User from any JsonElement representing a Github user. This JsonElement must be retrieved from the
     * Github API's Push Event
     *
     * @param userElement JsonElement representing the user
     *
     * @see PushEvent
     */
    public User(JsonElement userElement) {

        JsonObject userObj = userElement.getAsJsonObject();
        this.name = userObj.has("login") ? userObj.get("login").getAsString() : (userObj.has("username") ? userObj.get("username").getAsString() : (userObj.has("name") ? userObj.get("name").getAsString() : ""));
        this.email = userObj.has("email") ? userObj.get("email").getAsString() : "";
        this.htmlUrl = userObj.has("html_url") ? userObj.get("html_url").getAsString() : "";
        this.avatarUrl = userObj.has("avatar_url") ? userObj.get("avatar_url").getAsString() : "";
    }

    @Override
    public String toString() {

        return "User{" +
               "name='" + name + '\'' +
               ", email='" + email + '\'' +
               ", htmlUrl='" + htmlUrl + '\'' +
               ", avatarUrl='" + avatarUrl + '\'' +
               '}';
    }
}
