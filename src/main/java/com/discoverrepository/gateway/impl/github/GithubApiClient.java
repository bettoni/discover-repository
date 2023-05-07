package com.discoverrepository.gateway.impl.github;

import com.discoverrepository.gateway.impl.github.response.PaginatedGithubRepositoriesResult;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;

import static io.micronaut.http.HttpHeaders.ACCEPT;
import static io.micronaut.http.HttpHeaders.USER_AGENT;

@Client(id = "github")
@Header(name = USER_AGENT, value = "Discovery Repo App")
@Header(name = ACCEPT, value = "application/vnd.github.v3+json, application/json")
public interface GithubApiClient {
    @Get("/search/repositories?q={query}&per_page={limit}&sort=stars&order=desc")
    PaginatedGithubRepositoriesResult fetchRepositories(@QueryValue String query, Integer limit);
}
