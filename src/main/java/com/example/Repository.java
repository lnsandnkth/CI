package com.example;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;

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
     * JGit API instance used for calling commands on the repo
     *
     * @see Git
     */
    private Git git;

    /**
     * Directory the repository is located in
     */
    private String directory;

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

    public Git cloneRepository(String branch, String target) {

        if (this.git != null && this.directory != null)
            this.clean();

        // dir used for repo cloning
        File outDir = new File(target);
        if (outDir.exists()) {
            try {
                FileUtils.deleteDirectory(outDir);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        this.directory = target;

        // prepare command
        CloneCommand clone = Git.cloneRepository()
                                .setURI(this.cloneUrl)
                                .setBranch(branch);

        if (!outDir.exists() || outDir.isDirectory())
            clone.setDirectory(outDir);

        // clone the repository and checkout right branch
        try {
            this.git = clone.call();
        } catch (GitAPIException e) {
            e.printStackTrace();
            return null;
        }

        return this.git;
    }

    public void clean() {

        // close git resource
        try {
            git.clean().call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        git.close();

        this.git = null;

        // delete temporary directory
        File outDir = new File(this.directory);
        if (outDir.exists()) {
            try {
                FileUtils.deleteDirectory(outDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.directory = null;
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
