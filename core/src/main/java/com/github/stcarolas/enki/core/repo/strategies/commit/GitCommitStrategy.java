package com.github.stcarolas.enki.core.repo.strategies.commit;

import java.io.File;
import java.util.function.Function;

import com.github.stcarolas.enki.core.Repo;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;

import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GitCommitStrategy implements Function<String, Option<? extends Repo>> {

	private final Option<Repo> repo;

	@Override
	public Option<? extends Repo> apply(String commitMessage) {
		return repo
			.onEmpty(() -> log.error("dont try to commit to NULL repository"))
			.peek(it -> log.info("commiting to repository {}", it))
			.peek(
				repo -> repo.directory()
					.onEmpty(
						() -> log.error("try to download repo {} before commiting into that", repo)
					)
					.peek(dir -> log.info("using directory {}", dir))
					.peek(
						dir -> Option.of(commitMessage)
							.onEmpty(() -> log.error("missing commit message"))
							.peek( message -> this.commit(dir, message))
					)
			);
	}

	private Try<RevCommit> commit(File directory, String commitMessage){
		return Try.of(() -> Git.open(directory))
			.filterTry(git -> git.status().call().isClean())
			.andThenTry(git -> git.add().addFilepattern(".").call())
			.flatMapTry(
				git -> Option.of(commitMessage)
					.onEmpty(() -> log.error("missing commit message"))
					.toTry()
					.mapTry(message -> git.commit().setMessage(message).call())
			)
			.onFailure( error -> log.error("error: {}", error))
			.onSuccess(
				commit -> log.info(
					"Successful commit: {}",
					commit.getId()
				)
			);
	}


	public static Function<String, Option<? extends Repo>> viaGit(Repo repo) {
		return new GitCommitStrategy(Option.of(repo));
	}
}
