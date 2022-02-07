package com.example;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Repository {

    public String name, fullName, url, cloneUrl;

    public Repository(JsonElement repoElement) {

        JsonObject repoObj = repoElement.getAsJsonObject();
        this.name = repoObj.get("name").getAsString();
        this.fullName = repoObj.get("full_name").getAsString();
        this.url = repoObj.get("html_url").getAsString();
        this.cloneUrl = repoObj.get("clone_url").getAsString();
    }

    @Override
    public String toString() {

        return "Repository{" +
               "name='" + name + '\'' +
               ", fullName='" + fullName + '\'' +
               ", url='" + url + '\'' +
               ", cloneUrl='" + cloneUrl + '\'' +
               '}';
    }
}
