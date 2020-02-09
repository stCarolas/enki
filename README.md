# Enki 

 [![Release](https://github.com/stCarolas/Enki/workflows/Release/badge.svg)](https://github.com/stCarolas/Enki/actions?query=workflow%3ARelease)
 [![Version](https://img.shields.io/github/v/tag/stCarolas/Enki?label=version&sort=semver)](https://github.com/stCarolas/Enki/packages)
 [![Codacy Badge](https://api.codacy.com/project/badge/Grade/a631d4eeb0834e8f99035ce52c204acb)](https://app.codacy.com/manual/stcarolas/Enki?utm_source=github.com&utm_medium=referral&utm_content=stCarolas/Enki&utm_campaign=Badge_Grade_Dashboard)

1. ![Why](https://github.com/stCarolas/Enki#why)
1. ![Set Up With Maven](https://github.com/stCarolas/Enki#set-up-with-maven)
1. ![Set Up With Gradle](https://github.com/stCarolas/Enki#set-up-with-gradle)
1. ![Core Concepts](https://github.com/stCarolas/Enki/tree/master/core)
1. Providers:
    - ![Bitbucket](https://github.com/stCarolas/Enki/tree/master/providers/bitbucket)
    - ![Github](https://github.com/stCarolas/Enki/tree/master/providers/github)
    - ![Gitlab](https://github.com/stCarolas/Enki/tree/master/providers/gitlab)
    - ![Gitea](https://github.com/stCarolas/Enki/tree/master/providers/gitea)
1. Handlers:
    - ![Discord commit hook](https://github.com/stCarolas/Enki/tree/master/handlers/discord-commit-hook)
    - ![Mirror repo to Gitea](https://github.com/stCarolas/Enki/tree/master/handlers/gitea-mirror)
    - ![Archive GitHub repo](https://github.com/stCarolas/Enki/tree/master/handlers/github-archive-repo)
    - ![Sync with GoCD](https://github.com/stCarolas/Enki/tree/master/handlers/gocd-handlers)
    - ![JaCoCo configurator for maven projects](https://github.com/stCarolas/Enki/tree/master/handlers/jacoco)
    - ![Only print a list of found repositories](https://github.com/stCarolas/Enki/tree/master/handlers/logging-handlhandlers)
    - ![Collect all dependency from maven project](https://github.com/stCarolas/Enki/tree/master/handlers/logging-handlhandlers)
1. Tools:
    - ![Cli/Server Runner](https://github.com/stCarolas/Enki/tree/master/server)
    - ![Discord Bot](https://github.com/stCarolas/Enki/tree/master/tools/discord-bot)
    - ![Console Wrapper for Template Generator](https://github.com/stCarolas/Enki/tree/master/tools/generator)
1. ![Roadmap](https://github.com/stCarolas/Enki/milestones)

# Why

Sometimes we want to do some work with multiple repositories - add step with SonarQube to all 
pipelines, add JaCoCo to all projects, up library version everywhere, add helm charts, etc. 
There is where Enki can help you.

Enki takes all the problems with handling of cloning and iterating over multiple repositories, commiting and pushing them. All you need to do is write or reuse some handler which takes metadata and local cloned copy of repository and just does required job.

last check - committing, commiting, comitting.

# Set Up with Maven

Add github personal token with `read:packages` scope to settings.xml (use Personal Access Token from Github, not password)
```
<servers>
  <server>
    <id>enki-github-repo</id>
    <username>YOUR_GITHUB_USERNAME</username>
    <password>YOUR_GITHUB_TOKEN</password>
  </server>
</servers>
```

Add github repository to pom
```
  <repositories>
    <repository>
      <id>enki-github-repo</id>
      <url>https://maven.pkg.github.com/stCarolas/Enki</url>
    </repository>
  </repositories>
```

Add ![this](https://github.com/stCarolas/Enki/packages/70782) dependency to pom

# Set Up with Gradle

Add repository (use Personal Access Token from Github with `read:packages` scope, not password)
```
    maven { 
        credentials(PasswordCredentials) {
            username "<MY_USERNAME>"
            password "<MY_GITHUB_TOKEN>"
        }
        url "https://maven.pkg.github.com/stCarolas/Enki" 
    }
```

Add ![this](https://github.com/stCarolas/Enki/packages/70782) dependency to build.gradle
