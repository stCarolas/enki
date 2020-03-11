package com.github.stcarolas.enki.core.provider.strategies.download;

import static com.github.stcarolas.enki.core.util.Lifting.call;
import static io.vavr.API.Option;
import static io.vavr.API.Try;

import java.io.File;
import java.util.function.Supplier;

import com.github.stcarolas.enki.core.Repo;
import com.github.stcarolas.enki.core.transport.DefaultTransportConfigCallback;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;

import io.vavr.Function4;
import io.vavr.control.Option;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class GitCloneDownloadStrategy<T extends Repo> implements Supplier<File> {
// todo better naming for git clone subfunctions

	@With private final Option<String> sshUrl;
	@With private final Option<T> repository;
	@With private final Supplier<CloneCommand> cloneCommand;
	@With private final Supplier<DefaultTransportConfigCallback> transport;
	@With private final Function4<Supplier<DefaultTransportConfigCallback>, Supplier<CloneCommand>, String, File, CloneCommand> clone;

	public static final Supplier<CloneCommand> cloneCommandFn = 
		() -> Git.cloneRepository();

	public static final Supplier<DefaultTransportConfigCallback> transportFn = 
		() -> new DefaultTransportConfigCallback();

	public static final Function4<Supplier<DefaultTransportConfigCallback>, Supplier<CloneCommand>, String, File, CloneCommand> 
		cloneFn = ( transport, cloneCommand, url, dir ) -> 
			cloneCommand.get()
				.setURI(url)
				.setDirectory(dir)
				.setTransportConfigCallback(transport.get());

	@Override
	public File get(){
		return sshUrl
			.onEmpty(() -> log.error("missing ssh url to use for cloning"))
			.flatMap(
				url -> repository
					.onEmpty(
						() -> log.error("missing repository to clone for url {}", url)
					)
					.flatMap(
						repo -> call(repo::directory)
							.onEmpty(() -> log.error("missing any directory to clone into"))
					)
					.peek(
						dir -> Try(clone.apply(transport).apply(cloneCommand).apply(url).apply(dir)::call)
							.onFailure(
								error -> log.error("error while cloning {}: {}", url, error)
							)
							.onSuccess(
								git -> log.info("repository with url {} was cloned", url)
							)
					)
			).getOrNull();
	}

	public static <T extends Repo>Supplier<File> GitSshClone(T repo, String sshUrl){
		return GitCloneDownloadStrategy.builder()
			.sshUrl(Option(sshUrl))
			.repository(Option(repo))
			.transport(transportFn)
			.cloneCommand(cloneCommandFn)
			.clone(cloneFn)
			.build();
	}

}
