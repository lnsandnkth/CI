import com.example.Repository;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RepositoryTest {

    private Repository repo;
    private String targetBranch, targetDir;

    @Test
    @Order(0)
    @BeforeAll
    void Setup() {


        targetBranch = "testbranch";
        targetDir = "./test_temp/";


        String json = """
            {
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
            }
            """;

        repo = new Repository(JsonParser.parseString(json));
    }

    @Test
    @Order(1)
    @DisplayName("Ensuring that data is correctly parsed from JSON to Repository instance")
    public void EnsureData() {

        Assertions.assertAll(
            () -> Assertions.assertEquals("mxyns/wekhook_trash", repo.fullName),
            () -> Assertions.assertEquals("wekhook_trash", repo.name),
            () -> Assertions.assertEquals("https://github.com/mxyns/wekhook_trash.git", repo.cloneUrl),
            () -> Assertions.assertEquals("https://api.github.com/repos/mxyns/wekhook_trash/statuses/{sha}", repo.statusesUrl),
            () -> Assertions.assertEquals("https://github.com/mxyns/wekhook_trash", repo.url),
            () -> Assertions.assertNull(repo.directory())
                            );
    }

    @Test
    @Order(2)
    @DisplayName("Cloning the repository and checking for correct file system and Repository state")
    public void CloneTest() throws DirectoryNotEmptyException {

        File file = new File(targetDir);
        try {
            FileUtils.deleteDirectory(file);
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }

        // hasn't been cloned before
        Assertions.assertNull(repo.directory());

        Assertions.assertDoesNotThrow(
            () -> repo.cloneRepository(targetBranch, targetDir)
                                     );

        // ensure directory and git are correctly set
        Assertions.assertEquals(targetDir, repo.directory());
        Assertions.assertNotNull(repo.git());

        Assertions.assertAll(
            () -> Assertions.assertTrue(file.exists()),
            () -> Assertions.assertTrue(file.isDirectory()),
            () -> Assertions.assertNotNull(file.list()),
            () -> Assertions.assertTrue(() -> file.list().length > 0),
            () -> Assertions.assertEquals("refs/heads/" + targetBranch, repo.git().getRepository().getFullBranch())
                            );
    }

    @Test
    @Order(3)
    @DisplayName("Ensure Repository clean releases memory and file system resources")
    public void Clean() {

        Git git = repo.git();

        repo.clean();

        Assertions.assertAll(
            () -> Assertions.assertFalse(new File(targetDir).exists()),
            () -> Assertions.assertNull(repo.git()),
            () -> Assertions.assertNull(repo.directory()),
            () -> Assertions.assertNull(git.getRepository().getFullBranch())
                            );


    }
}
