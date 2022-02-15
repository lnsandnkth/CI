package com.example;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.gradle.internal.impldep.javax.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.util.Arrays;

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
     * Statuses url
     */
    public final String statusesUrl;

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
        this.statusesUrl = repoObj.get("statuses_url").getAsString();
    }

    /**
     * Clone the repository based on the clone URL retrieved in the JSON and checkout the given branch.
     *
     * @param branch branch to check out after commit
     * @param target directory to clone the repository in. must be empty!
     *
     * @return JGit instance that allows to call commands on the repository
     *
     * @see Repository#git x the returned value
     * @see Git#cloneRepository() JGit API method called
     *
     * @throws DirectoryNotEmptyException when the target directory is not empty. not emptied automatically for safety reasons
     */
    public Git cloneRepository(String branch, String target) throws DirectoryNotEmptyException {

        if (this.git != null && this.directory != null)
            this.clean();

        // dir used for repo cloning
        File outDir = new File(target);
        if (outDir.exists()) {

            String[] list = outDir.list();
            if (list == null || list.length != 0) {
                System.out.println(Arrays.toString(list));
                throw new DirectoryNotEmptyException("The cloning destination " + target + " is not empty!");
            }

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

    /**
     * Clean up this repository's disk/memory usage by releasing the JGit reference and deleting the working dir along
     * with its content
     *
     * @see Repository#directory set to null after method call
     * @see Repository#git set to null after method call
     * @see Git#clean() clean JGit command called
     * @see Git#close() freeing JGit command called
     */
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
                // shouldn't be risky bc directory was ensured to be empty before cloning
                FileUtils.deleteDirectory(outDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.directory = null;
    }

    /**
     * Get the directory the repository has been cloned in.
     *
     * @return this repository's directory path or null if the repository hasn't been cloned / has been cleaned
     *
     * @see Repository#directory the returned value
     * @see Repository#cloneRepository(String, String) the method that sets the Repository#directory
     * @see Repository#clean() the method that resets Repository#directory
     */
    @Nullable
    public String directory() {

        return this.directory;
    }

    /**
     * Get the JGit Git instance of the repository has been cloned in.
     *
     * @return this repository's git handle
     *
     * @see Repository#git the returned value
     * @see Repository#cloneRepository(String, String) the method that sets the Repository#git
     * @see Repository#clean() the method that resets Repository#git
     */
    @Nullable
    public Git git() {

        return this.git;
    }

    /**
     * Formats the repo to a readable string format
     * @return the repo in a string format
     */
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
