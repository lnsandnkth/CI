package com.example;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;

import java.io.File;

/**
 * A class representing a Github Repository retrieved from a PushEvent
 *
 * @see PushEvent
 */
public class Repository {

    /**
     * Name of the Repository as on Github
     */
    public final String name;

    /**
     * Full name of the Repository : {owner.name}/{repository.name}
     */
    public final String fullName;

    /**
     * URL linking to the Repository's Github page
     */
    public final String url;

    /**
     * URL to be used when cloning the Repository
     */
    public final String cloneUrl;

    /**
     * The Repository's owner as a User object
     *
     * @see User
     */
    public final User owner;

    /**
     * Make a new Repository from a JsonElement representing a Github repo. This JsonElement must be retrieved from the
     * Github API's Push Event
     *
     * @param repoElement JsonElement representing the repo
     *
     * @see PushEvent
     */
    public Repository(JsonElement repoElement) {

        JsonObject repoObj = repoElement.getAsJsonObject();
        this.name = repoObj.get("name").getAsString();
        this.fullName = repoObj.get("full_name").getAsString();
        this.url = repoObj.get("html_url").getAsString();
        this.cloneUrl = repoObj.get("clone_url").getAsString();
        this.owner = new User(repoObj.get("owner"));
    }

    public CloneCommand cloneRepository(String branch, File target) {

        CloneCommand clone = Git.cloneRepository()
            .setURI(this.cloneUrl)
            .setBranch(branch);

        if (target != null && (!target.exists() || target.isDirectory()))
            clone.setDirectory(target);

        return clone;
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
