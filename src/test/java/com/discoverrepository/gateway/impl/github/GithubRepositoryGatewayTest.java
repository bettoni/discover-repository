package com.discoverrepository.gateway.impl.github;

import com.discoverrepository.domain.Repository;
import com.discoverrepository.gateway.impl.github.response.GithubRepository;
import com.discoverrepository.gateway.impl.github.response.PaginatedGithubRepositoriesResult;
import com.discoverrepository.usecase.FindRepositoryFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GithubRepositoryGatewayTest {

    @Mock
    private GithubApiClient remoteApi;
    @InjectMocks
    private GithubRepositoryGateway gateway;

    @Test
    void shouldNotAllowLimitBiggerThan100() {
        FindRepositoryFilter filter = new FindRepositoryFilter(null, null, 101);

        assertThatThrownBy(() -> gateway.fetchRepositories(filter))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldNotAllowLimitSmallerThan0() {
        FindRepositoryFilter filter = new FindRepositoryFilter(null, null, 0);

        assertThatThrownBy(() -> gateway.fetchRepositories(filter))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldSearchForAllRepositories() {
        PaginatedGithubRepositoriesResult remoteApiCallResult = PaginatedGithubRepositoriesResult.builder()
                .items(List.of(createDefaultGithubRepository(), createDefaultGithubRepository()))
                .build();
        when(remoteApi.fetchRepositories(anyString(), anyInt())).thenReturn(remoteApiCallResult);
        FindRepositoryFilter filter = FindRepositoryFilter.defaultFilter();

        List<Repository> repositories = gateway.fetchRepositories(filter);

        assertThat(repositories).hasSize(2);
    }

    @Test
    void shouldHandleEmptyResult() {
        PaginatedGithubRepositoriesResult remoteApiCallResult =
                PaginatedGithubRepositoriesResult.builder().items(List.of()).build();
        when(remoteApi.fetchRepositories(anyString(), anyInt())).thenReturn(remoteApiCallResult);

        FindRepositoryFilter filter = FindRepositoryFilter.defaultFilter();

        assertThat(gateway.fetchRepositories(filter)).isEmpty();
    }

    private static GithubRepository createDefaultGithubRepository() {
        return GithubRepository.builder()
                .htmlUrl("https://github.com/dtrupenn/Tetris")
                .language("Assembly")
                .privateRepo(false)
                .stargazersCount(1)
                .createdAt(OffsetDateTime.parse("2018-03-16T00:57:51Z"))
                .description("A C implementation of Tetris using Pennsim through LC4")
                .name("Tetris")
                .fullName("dtrupenn/Tetris")
                .build();
    }
}