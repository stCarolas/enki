package com.github.stcarolas.enki.core;

import com.github.stcarolas.enki.core.model.AnotherTestProvider;
import com.github.stcarolas.enki.core.model.AnotherTestRepo;
import com.github.stcarolas.enki.core.model.TestProvider;
import com.github.stcarolas.enki.core.model.TestRepo;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class EnkiRunnerTest {

    @Test
    void should_accept_generics() {
        //expect:
        RepoProvider<TestRepo> provider = TestProvider.builder()
                    .testRepos(Arrays.asList(new TestRepo("first")))
                    .build();
        EnkiRunner.<TestRepo>builder()
                    .provider(provider)
                    .build();
    }

    @Test
    void should_accept_two_different_providers_generics() {
        //expect:
        RepoProvider provider = TestProvider.builder()
                    .testRepos(Arrays.asList(new TestRepo("first")))
                    .build();
        RepoProvider anotherProvider = AnotherTestProvider.builder()
                    .testRepos(Arrays.asList(new AnotherTestRepo("first")))
                    .build();
        EnkiRunner build = EnkiRunner.builder()
                    .providers(Arrays.asList(anotherProvider, provider))
                    .build();
    }
}
