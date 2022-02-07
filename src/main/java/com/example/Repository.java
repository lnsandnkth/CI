package com.example;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Repository {

    public final String name, fullName, url, cloneUrl;
    public final User owner;

    public Repository(JsonElement repoElement) {

        JsonObject repoObj = repoElement.getAsJsonObject();
        this.name = repoObj.get("name").getAsString();
        this.fullName = repoObj.get("full_name").getAsString();
        this.url = repoObj.get("html_url").getAsString();
        this.cloneUrl = repoObj.get("clone_url").getAsString();
        this.owner = new User(repoObj.get("owner"));
    }

    @Override
    public String toString() {

        return "Repository{" +
               "name='" + name + '\'' +
               ", fullName='" + fullName + '\'' +
               ", url='" + url + '\'' +
               ", cloneUrl='" + cloneUrl + '\'' +
               ", owner=" + owner +
               '}';
    }
}
