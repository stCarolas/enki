package com.github.stcarolas.enki.core.model;

import com.github.stcarolas.enki.core.RepoProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Builder
@AllArgsConstructor
public class TestProvider implements RepoProvider<TestRepo> {

    private List<TestRepo> testRepos;

    @Override public List<TestRepo> getRepos() {
        return testRepos;
    }
}
