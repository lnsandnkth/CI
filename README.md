# CI
This project implements a continuous integration server, as stated by [assignment #2 in the course DD2480](https://canvas.kth.se/courses/31884/assignments/185708).


### How to build and run
0. Make sure to have any JRE installed
1. Run `./gradlew check` to set up the environment
2. Run `./gradlew clean test` to run the tests
3. Run `./gradlew javadoc` to generate Javadoc HTML. The files will be located in `build/docs`. 
4. Start the server by running `./gradlew run`.
Note: environment variables `Token`needs to be set and `PORT`specified (otherwise port 8080 will be used) in `conf.json`.
The token is a GitHub "Personal access token" with at least `repo` permission.
5. Configure a repo webhook to your server address.

### Way of working

| State         |                                           Checklist                                            | done |
|---------------|:----------------------------------------------------------------------------------------------:|-----:|
| Seeded        |         The team mission has been defined in terms of the opportunities and outcomes.          |    x | 
|               |                         Constraints on the team's operation are known.                         |    x |
|               |                           Mechanisms to grow the team are in place.                            |    x |
|               |                            The composition of the team is defined.                             |    x |
|               |             Any constraints on where and how the work is carried out are defined.              |    x |
|               |                           The team's responsibilities are outlined.                            |    x |
|               |                             The level of team commitment is clear.                             |    x |
|               |                             Required competencies are identified.                              |    x |
|               |                                  The team size is determined.                                  |    x |
|               |                                 Governance rules are defined.                                  |    x |
|               |                                Leadership model is determined.                                 |    x |
| Formed        |                          Individual responsibilities are understood.                           |    x |
|               |            Enough team members have been recruited to enable the work to progress.             |    x |
|               |   Every team member understands how the team is organized and what their individual role is.   |    x |
|               |                     All team members understand how to perform their work.                     |    x |
|               |   The team members have met (perhaps virtually) and are beginning to get to know each other.   |    x |
|               | The team members understand their responsibilities and how they align with their competencies. |    x |
|               |                                Team members are accepting work.                                |    x |
|               |       Any external collaborators (organizations, teams and individuals) are identified.        |    x |
|               |                        Team communication mechanisms have been defined.                        |    x |
|               |                  Each team member commits to working on the team as defined.                   |    x |
| Collaborating |                           The team is working as one cohesive unit.                            |    x |
|               |                       Communication within the team is open and honest.                        |    x |
|               |                       The team is focused on achieving the team mission.                       |    x |
|               |                          The team members know and trust each other.                           |    x |
| Performing    |                          The team consistently meets its commitments.                          |    x |
|               |                     The team continuously adapts to the changing context.                      |    x |
|               |                The team identifies and addresses problems without outside help.                |    x |
|               |    Effective progress is being achieved with minimal avoidable backtracking and reworking.     |    x |
|               |   Wasted work and the potential for wasted work are continuously identified and eliminated.    |    x |
| Adjourned     |                 The team responsibilities have been handed over or fulfilled.                  |    x |
|               |                 The team members are available for assignment to other teams.                  |    x |
|               |             No further effort is being put in by the team to complete the mission.             |    x |




*State we are in*: Adjourned <br>
*Why*: Every (relevant) checkpoint in the earlier state has been achieved. The assigment is now completed and the team is ready for other "teams" (next assignment)<br>
*What are the obstacles to reach the next state*:  There is no next state to reach. Almost all of the checkpoints come naturally from the way the assignments are specified and the way the team works.
Some checkpoints are ambigious and interpreted quite freely. <br>

### Reasons for P+
- we configured a heroku remote server for this project to run everything, instead of using our laptops as servers. This was done so we there won't be several servers running at the same time, resulting in possible conflicts. And because that way we won't need to update the webhooks all the time when getting a new ngrok link. Also the database would have a complete build history, since when we implemented the db functionalities. But we could not afford the more expensive heroku service with more ram (500kr/mon). So from the point in the project where it could clone and build a new commit, the server needed more ram as was possible, and it could not build and test new commits anymore. Only sometimes it went through, as the ram needed was only slightly above the max capacity.
- we also push the pending status to github, not only passed or fail
- we generate a build history and a build info page, with more than just the build log, commit_id and timestamp. We also include the build and test result (success, or fail), and the name of who commited.
    - we also styled it nicely with css
    - you can also click the commit_id in the buildhistory to get to the info of only the one build
- we also linked most commits (typically 90%) to an issue describing the feature / commit.

### Authors

- [@lnsandnkth](https://www.github.com/lnsandnkth) *Leon Sandner* :
    - reviewed issues: 23/28,20/22,21,7,10,45,50,51,52,47
    - url mapping
    - generating build history and build info pages
    - fixed: folder deletion, tried to fix ram usage for remote server(did not work)
- [@mxyns](https://www.github.com/mxyns) *Maxence Younsi*
    - json payload parsing with Gson into PushEvent/Repository/Commit/User objects (+ doc)
    - gradle project linking, build and test using Gradle Tooling API lib (+ doc)
    - git cloning, branch/commit/ref checkout & cleanup with JGit (+ doc)
    - pair programming/debugging commit status with [@noahra](https://www.github.com/noahra)
    - commit link in history, pending commit status
    - reviewed some PRs, made tests for gradle, git, json parsing
- [@noahra](https://www.github.com/noahra) *Noah Rahimzadagan:*
    - Implemented status update functionality for commits
    - Reviewed some issues
- [@sandy-e](https://www.github.com/sandy-e) *Sanherib Elia*:
    - project description
    - running and testing instructions
    - generated Javadoc
    - evaluated the team (SEMAT)
- [@yuxin-miao](https://www.github.com/yuxin-miao) *Xinmiao Yu* :
    - connect database sqlite-jdbc to the project
    - add data push to and pull from database
    - reviewed issues  
