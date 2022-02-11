import com.example.User;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {

    static JsonElement pushEventRequest;

    @BeforeAll
    @DisplayName("Make a reference JSON request for testing on real data")
    public static void MakeJSON() {

        pushEventRequest = JsonParser.parseString("""
                                                      {
                                                        "ref": "refs/heads/testbranch",
                                                        "before": "e5d5a63031f479beab0a9ebfc8e145bc175dbf26",
                                                        "after": "62e77d85d3efd477aeb55cdda4a7c04e28ff1be0",
                                                        "repository": {
                                                          "id": 456686913,
                                                          "node_id": "R_kgDOGzh9QQ",
                                                          "name": "wekhook_trash",
                                                          "full_name": "mxyns/wekhook_trash",
                                                          "private": false,
                                                          "owner": {
                                                            "name": "mxyns",
                                                            "email": "5208681+mxyns@users.noreply.github.com",
                                                            "login": "mxyns",
                                                            "id": 5208681,
                                                            "node_id": "MDQ6VXNlcjUyMDg2ODE=",
                                                            "avatar_url": "https://avatars.githubusercontent.com/u/5208681?v=4",
                                                            "gravatar_id": "",
                                                            "url": "https://api.github.com/users/mxyns",
                                                            "html_url": "https://github.com/mxyns",
                                                            "followers_url": "https://api.github.com/users/mxyns/followers",
                                                            "following_url": "https://api.github.com/users/mxyns/following{/other_user}",
                                                            "gists_url": "https://api.github.com/users/mxyns/gists{/gist_id}",
                                                            "starred_url": "https://api.github.com/users/mxyns/starred{/owner}{/repo}",
                                                            "subscriptions_url": "https://api.github.com/users/mxyns/subscriptions",
                                                            "organizations_url": "https://api.github.com/users/mxyns/orgs",
                                                            "repos_url": "https://api.github.com/users/mxyns/repos",
                                                            "events_url": "https://api.github.com/users/mxyns/events{/privacy}",
                                                            "received_events_url": "https://api.github.com/users/mxyns/received_events",
                                                            "type": "User",
                                                            "site_admin": false
                                                          },
                                                          "html_url": "https://github.com/mxyns/wekhook_trash",
                                                          "description": null,
                                                          "fork": false,
                                                          "url": "https://github.com/mxyns/wekhook_trash",
                                                          "forks_url": "https://api.github.com/repos/mxyns/wekhook_trash/forks",
                                                          "keys_url": "https://api.github.com/repos/mxyns/wekhook_trash/keys{/key_id}",
                                                          "collaborators_url": "https://api.github.com/repos/mxyns/wekhook_trash/collaborators{/collaborator}",
                                                          "teams_url": "https://api.github.com/repos/mxyns/wekhook_trash/teams",
                                                          "hooks_url": "https://api.github.com/repos/mxyns/wekhook_trash/hooks",
                                                          "issue_events_url": "https://api.github.com/repos/mxyns/wekhook_trash/issues/events{/number}",
                                                          "events_url": "https://api.github.com/repos/mxyns/wekhook_trash/events",
                                                          "assignees_url": "https://api.github.com/repos/mxyns/wekhook_trash/assignees{/user}",
                                                          "branches_url": "https://api.github.com/repos/mxyns/wekhook_trash/branches{/branch}",
                                                          "tags_url": "https://api.github.com/repos/mxyns/wekhook_trash/tags",
                                                          "blobs_url": "https://api.github.com/repos/mxyns/wekhook_trash/git/blobs{/sha}",
                                                          "git_tags_url": "https://api.github.com/repos/mxyns/wekhook_trash/git/tags{/sha}",
                                                          "git_refs_url": "https://api.github.com/repos/mxyns/wekhook_trash/git/refs{/sha}",
                                                          "trees_url": "https://api.github.com/repos/mxyns/wekhook_trash/git/trees{/sha}",
                                                          "statuses_url": "https://api.github.com/repos/mxyns/wekhook_trash/statuses/{sha}",
                                                          "languages_url": "https://api.github.com/repos/mxyns/wekhook_trash/languages",
                                                          "stargazers_url": "https://api.github.com/repos/mxyns/wekhook_trash/stargazers",
                                                          "contributors_url": "https://api.github.com/repos/mxyns/wekhook_trash/contributors",
                                                          "subscribers_url": "https://api.github.com/repos/mxyns/wekhook_trash/subscribers",
                                                          "subscription_url": "https://api.github.com/repos/mxyns/wekhook_trash/subscription",
                                                          "commits_url": "https://api.github.com/repos/mxyns/wekhook_trash/commits{/sha}",
                                                          "git_commits_url": "https://api.github.com/repos/mxyns/wekhook_trash/git/commits{/sha}",
                                                          "comments_url": "https://api.github.com/repos/mxyns/wekhook_trash/comments{/number}",
                                                          "issue_comment_url": "https://api.github.com/repos/mxyns/wekhook_trash/issues/comments{/number}",
                                                          "contents_url": "https://api.github.com/repos/mxyns/wekhook_trash/contents/{+path}",
                                                          "compare_url": "https://api.github.com/repos/mxyns/wekhook_trash/compare/{base}...{head}",
                                                          "merges_url": "https://api.github.com/repos/mxyns/wekhook_trash/merges",
                                                          "archive_url": "https://api.github.com/repos/mxyns/wekhook_trash/{archive_format}{/ref}",
                                                          "downloads_url": "https://api.github.com/repos/mxyns/wekhook_trash/downloads",
                                                          "issues_url": "https://api.github.com/repos/mxyns/wekhook_trash/issues{/number}",
                                                          "pulls_url": "https://api.github.com/repos/mxyns/wekhook_trash/pulls{/number}",
                                                          "milestones_url": "https://api.github.com/repos/mxyns/wekhook_trash/milestones{/number}",
                                                          "notifications_url": "https://api.github.com/repos/mxyns/wekhook_trash/notifications{?since,all,participating}",
                                                          "labels_url": "https://api.github.com/repos/mxyns/wekhook_trash/labels{/name}",
                                                          "releases_url": "https://api.github.com/repos/mxyns/wekhook_trash/releases{/id}",
                                                          "deployments_url": "https://api.github.com/repos/mxyns/wekhook_trash/deployments",
                                                          "created_at": 1644270114,
                                                          "updated_at": "2022-02-07T21:41:54Z",
                                                          "pushed_at": 1644273179,
                                                          "git_url": "git://github.com/mxyns/wekhook_trash.git",
                                                          "ssh_url": "git@github.com:mxyns/wekhook_trash.git",
                                                          "clone_url": "https://github.com/mxyns/wekhook_trash.git",
                                                          "svn_url": "https://github.com/mxyns/wekhook_trash",
                                                          "homepage": null,
                                                          "size": 0,
                                                          "stargazers_count": 0,
                                                          "watchers_count": 0,
                                                          "language": null,
                                                          "has_issues": true,
                                                          "has_projects": true,
                                                          "has_downloads": true,
                                                          "has_wiki": true,
                                                          "has_pages": false,
                                                          "forks_count": 0,
                                                          "mirror_url": null,
                                                          "archived": false,
                                                          "disabled": false,
                                                          "open_issues_count": 0,
                                                          "license": null,
                                                          "allow_forking": true,
                                                          "is_template": false,
                                                          "topics": [],
                                                          "visibility": "public",
                                                          "forks": 0,
                                                          "open_issues": 0,
                                                          "watchers": 0,
                                                          "default_branch": "master",
                                                          "stargazers": 0,
                                                          "master_branch": "master"
                                                        },
                                                        "pusher": {
                                                          "name": "mxyns",
                                                          "email": "5208681+mxyns@users.noreply.github.com"
                                                        },
                                                        "sender": {
                                                          "login": "mxyns",
                                                          "id": 5208681,
                                                          "node_id": "MDQ6VXNlcjUyMDg2ODE=",
                                                          "avatar_url": "https://avatars.githubusercontent.com/u/5208681?v=4",
                                                          "gravatar_id": "",
                                                          "url": "https://api.github.com/users/mxyns",
                                                          "html_url": "https://github.com/mxyns",
                                                          "followers_url": "https://api.github.com/users/mxyns/followers",
                                                          "following_url": "https://api.github.com/users/mxyns/following{/other_user}",
                                                          "gists_url": "https://api.github.com/users/mxyns/gists{/gist_id}",
                                                          "starred_url": "https://api.github.com/users/mxyns/starred{/owner}{/repo}",
                                                          "subscriptions_url": "https://api.github.com/users/mxyns/subscriptions",
                                                          "organizations_url": "https://api.github.com/users/mxyns/orgs",
                                                          "repos_url": "https://api.github.com/users/mxyns/repos",
                                                          "events_url": "https://api.github.com/users/mxyns/events{/privacy}",
                                                          "received_events_url": "https://api.github.com/users/mxyns/received_events",
                                                          "type": "User",
                                                          "site_admin": false
                                                        },
                                                        "created": false,
                                                        "deleted": false,
                                                        "forced": false,
                                                        "base_ref": null,
                                                        "compare": "https://github.com/mxyns/wekhook_trash/compare/e5d5a63031f4...62e77d85d3ef",
                                                        "commits": [
                                                          {
                                                            "id": "62e77d85d3efd477aeb55cdda4a7c04e28ff1be0",
                                                            "tree_id": "3f3a4185d8ebb3aca858b2b4baa67e6e5078c145",
                                                            "distinct": true,
                                                            "message": "Update README.md",
                                                            "timestamp": "2022-02-07T23:32:59+01:00",
                                                            "url": "https://github.com/mxyns/wekhook_trash/commit/62e77d85d3efd477aeb55cdda4a7c04e28ff1be0",
                                                            "author": {
                                                              "name": "Maxou",
                                                              "email": "5208681+mxyns@users.noreply.github.com",
                                                              "username": "mxyns"
                                                            },
                                                            "committer": {
                                                              "name": "GitHub",
                                                              "email": "noreply@github.com",
                                                              "username": "web-flow"
                                                            },
                                                            "added": [],
                                                            "removed": [],
                                                            "modified": [
                                                              "README.md"
                                                            ]
                                                          }
                                                        ],
                                                        "head_commit": {
                                                          "id": "62e77d85d3efd477aeb55cdda4a7c04e28ff1be0",
                                                          "tree_id": "3f3a4185d8ebb3aca858b2b4baa67e6e5078c145",
                                                          "distinct": true,
                                                          "message": "Update README.md",
                                                          "timestamp": "2022-02-07T23:32:59+01:00",
                                                          "url": "https://github.com/mxyns/wekhook_trash/commit/62e77d85d3efd477aeb55cdda4a7c04e28ff1be0",
                                                          "author": {
                                                            "name": "Maxou",
                                                            "email": "5208681+mxyns@users.noreply.github.com",
                                                            "username": "mxyns"
                                                          },
                                                          "committer": {
                                                            "name": "GitHub",
                                                            "email": "noreply@github.com",
                                                            "username": "web-flow"
                                                          },
                                                          "added": [],
                                                          "removed": [],
                                                          "modified": [
                                                            "README.md"
                                                          ]
                                                        }
                                                      }""");
    }

    @Test
    @DisplayName("Constructor call with repository owner JSON")
    public void RepoOwner() {

        Assertions.assertDoesNotThrow(
            () -> {
                User owner = new User(pushEventRequest.getAsJsonObject().get("repository").getAsJsonObject().get("owner"));
                Assertions.assertEquals(owner.name, "mxyns");
                Assertions.assertEquals(owner.email, "5208681+mxyns@users.noreply.github.com");
                Assertions.assertEquals(owner.avatarUrl, "https://avatars.githubusercontent.com/u/5208681?v=4");
                Assertions.assertEquals(owner.htmlUrl, "https://github.com/mxyns");
                System.out.println(owner);
            }
                                     );
    }

    @Test
    @DisplayName("Constructor call with pusher JSON")
    public void Pusher() {

        Assertions.assertDoesNotThrow(
            () -> {
                User pusher = new User(pushEventRequest.getAsJsonObject().get("pusher"));
                Assertions.assertEquals(pusher.name, "mxyns");
                Assertions.assertEquals(pusher.email, "5208681+mxyns@users.noreply.github.com");
                Assertions.assertEquals(pusher.avatarUrl, "");
                Assertions.assertEquals(pusher.htmlUrl, "");
                System.out.println(pusher);
            }
                                     );
    }

    @Test
    @DisplayName("Constructor call with sender JSON")
    public void Sender() {

        Assertions.assertDoesNotThrow(
            () -> {
                User sender = new User(pushEventRequest.getAsJsonObject().get("sender"));
                Assertions.assertEquals(sender.name, "mxyns");
                Assertions.assertEquals(sender.email, "");
                Assertions.assertEquals(sender.avatarUrl, "https://avatars.githubusercontent.com/u/5208681?v=4");
                Assertions.assertEquals(sender.htmlUrl, "https://github.com/mxyns");
                System.out.println(sender);
            }
                                     );
    }

    @Test
    @DisplayName("Constructor call with commit author JSON")
    public void CommitAuthor() {

        Assertions.assertDoesNotThrow(
            () -> {
                User author = new User(pushEventRequest.getAsJsonObject().get("head_commit").getAsJsonObject().get("author"));
                Assertions.assertEquals(author.name, "mxyns");
                Assertions.assertEquals(author.email, "5208681+mxyns@users.noreply.github.com");
                Assertions.assertEquals(author.avatarUrl, "");
                Assertions.assertEquals(author.htmlUrl, "");
                System.out.println(author);
            }
                                     );
    }

    @Test
    @DisplayName("Constructor call with commit committer JSON")
    public void CommitCommitter() {

        Assertions.assertDoesNotThrow(
            () -> {
                User committer = new User(pushEventRequest.getAsJsonObject().get("head_commit").getAsJsonObject().get("committer"));
                Assertions.assertEquals(committer.name, "web-flow");
                Assertions.assertEquals(committer.email, "noreply@github.com");
                Assertions.assertEquals(committer.avatarUrl, "");
                Assertions.assertEquals(committer.htmlUrl, "sdf");
                System.out.println(committer);
            }
                                     );
    }


}
