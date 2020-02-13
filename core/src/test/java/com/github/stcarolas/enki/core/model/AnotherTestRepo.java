package com.github.stcarolas.enki.core.model;

import com.github.stcarolas.enki.core.CloneURLType;
import com.github.stcarolas.enki.core.Repo;
import com.github.stcarolas.enki.core.RepoProvider;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class AnotherTestRepo implements Repo {

    private final String name;

    @Override public UUID getId() {
        return UUID.randomUUID();
    }

    @Override public String getName() {
        return name;
    }

    @Override public Map<CloneURLType, String> getCloneUrls() {
        return Collections.emptyMap();
    }

    @Override public Optional<File> getDirectory() {
        return Optional.empty();
    }

    @Override public RepoProvider getRepoProvider() {
        return null;
    }

    @Override public void commitAndPush(String commitMessage) {

    }
}
