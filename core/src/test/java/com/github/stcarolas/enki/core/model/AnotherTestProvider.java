package com.github.stcarolas.enki.core.model;

import com.github.stcarolas.enki.core.RepoProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Builder
@AllArgsConstructor
public class AnotherTestProvider implements RepoProvider<AnotherTestRepo> {

    private List<AnotherTestRepo> testRepos;

    @Override public List<AnotherTestRepo> getRepos() {
        return testRepos;
    }
}
