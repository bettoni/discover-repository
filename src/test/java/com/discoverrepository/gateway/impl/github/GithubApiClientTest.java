package com.discoverrepository.gateway.impl.github;

import com.discoverrepository.gateway.impl.github.response.PaginatedGithubRepositoriesResult;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.github.tomakehurst.wiremock.matching.MatchResult;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
@WireMockTest(httpPort = 9090)
class GithubApiClientTest {

    @Inject
    GithubApiClient apiClient;

    @Test
    void shouldConvertPayload() {
        stubFor(requestMatching(request -> MatchResult.of(request.getUrl().contains("/search/repositories")))
                .willReturn(ok()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("repositories.json")));

        PaginatedGithubRepositoriesResult result = apiClient.fetchRepositories("created:>1999-01-01", 10);

        assertThat(result).isNotNull();
        assertThat(result.getItems()).isNotEmpty();
    }


    @Test
    void shouldConvertEmptyPayload() {
        stubFor(requestMatching(request -> MatchResult.of(request.getUrl().contains("/search/repositories")))
                .willReturn(ok()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("empty_result.json")));

        PaginatedGithubRepositoriesResult result = apiClient.fetchRepositories("created:>1999-01-01", 10);

        assertThat(result).isNotNull();
        assertThat(result.getItems()).isEmpty();
    }

}