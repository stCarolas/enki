package com.github.stcarolas.enki.core;

import java.util.function.Function;

import com.github.stcarolas.enki.core.repo.remote.RemoteRepo;
import com.github.stcarolas.enki.core.repo.remote.RemoteRepoFactory;

import io.vavr.collection.Seq;
import io.vavr.control.Try;

public interface RepoProvider extends Function<RemoteRepoFactory, Try<Seq<RemoteRepo>>>{}
