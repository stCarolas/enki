package com.github.stcarolas.enki.core.provider.strategies.download;

import static com.github.stcarolas.enki.core.util.Lifting.call;
import static io.vavr.API.*;

import java.io.File;
import java.util.function.Supplier;

import com.github.stcarolas.enki.core.Repo;
import com.github.stcarolas.enki.core.transport.DefaultTransportConfigCallback;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;

import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Function3;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class GitCloneDownloadStrategy<T extends Repo> implements Supplier<File> {

	private final Option<String> sshUrl;
	private final Option<T> repository;

	protected Supplier<CloneCommand> cloneCommand = () -> Git.cloneRepository();

	protected Function3<Supplier<CloneCommand>, String, File, Try<Git>> clone = (cloneCommand, url, dir) -> 
		Try(
			() -> cloneCommand.get()
				.setURI(url)
				.setDirectory(dir)
				.setTransportConfigCallback(new DefaultTransportConfigCallback())
				.call()
		);

	@Override
	public File get(){
		return sshUrl
			.onEmpty(() -> log.error("missing ssh url to use for cloning"))
			.flatMap(
				url -> repository
					.onEmpty(() -> log.error("missing repository to clone for url {}", url))
					.flatMap(
						repo -> call(repo::directory)
							.onEmpty(() -> log.error("missing any directory to clone into"))
					)
					.peek(
						dir -> clone.apply(cloneCommand).apply(url).apply(dir)
							.onFailure(error -> log.error("error while cloning {}: {}", url, error))
							.onSuccess(git -> log.info("repository with url {} was cloned", url))
					)
			).getOrNull();
	}

	public static <T extends Repo>Supplier<File> gitSshClone(T repo, String sshUrl){
		return new GitCloneDownloadStrategy<>(Option(sshUrl), Option(repo));
	}

}
