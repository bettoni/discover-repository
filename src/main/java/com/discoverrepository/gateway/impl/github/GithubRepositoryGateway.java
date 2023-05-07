package com.discoverrepository.gateway.impl.github;

import com.discoverrepository.domain.Repository;
import com.discoverrepository.gateway.RepositoryGateway;
import com.discoverrepository.gateway.impl.github.response.GithubRepository;
import com.discoverrepository.gateway.impl.github.response.PaginatedGithubRepositoriesResult;
import com.discoverrepository.usecase.FindRepositoryFilter;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Singleton
public class GithubRepositoryGateway implements RepositoryGateway {

    private final GithubApiClient githubApiClient;

    @Override
    public List<Repository> fetchRepositories(FindRepositoryFilter filter) {
        if (filterHasInvalidLimit(filter)) {
            throw new IllegalArgumentException();
        }

        String queryParam = GithubQueryBuilder.createQuery(filter);
        PaginatedGithubRepositoriesResult result = githubApiClient.fetchRepositories(queryParam, filter.limit());

        return result.getItems().stream()
                .map(this::toRepository)
                .collect(Collectors.toList());
    }

    private static boolean filterHasInvalidLimit(FindRepositoryFilter filter) {
        return filter.limit() < 0 || filter.limit() > 100;
    }

    private Repository toRepository(GithubRepository githubRepository) {
        return Repository.builder()
                .name(githubRepository.getFullName())
                .description(githubRepository.getDescription())
                .starCount(githubRepository.getStargazersCount())
                .url(githubRepository.getHtmlUrl())
                .language(githubRepository.getLanguage())
                .createdAt(githubRepository.getCreatedAt())
                .build();
    }
}
