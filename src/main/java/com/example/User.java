package com.example;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class User {

    public final String
        name,
        email,
        htmlUrl,
        avatarUrl;

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
