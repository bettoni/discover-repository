package com.discoverrepository.api.http;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.github.tomakehurst.wiremock.matching.MatchResult;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.Matchers.*;

@MicronautTest
@WireMockTest(httpPort = 9090)
class DiscoverRepositoryControllerTest {

    @Test
    void shouldReturnTopStaredRepositories(RequestSpecification spec) {
        stubFor(requestMatching(request -> MatchResult.of(request.getUrl().contains("/search/repositories")))
                .willReturn(ok()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("two_repositories.json")));

        spec.when()
                .get("/repositories")
                .prettyPeek()
                .then()
                .statusCode(200)
                .assertThat()
                .body("", hasSize(2))
                .body("[0].name", is("freeCodeCamp/freeCodeCamp"))
                .body("[0].description", is("freeCodeCamp.org's open-source codebase and curriculum. Learn to code for free."))
                .body("[0].starCount", is(365505))
                .body("[0].url", is("https://github.com/freeCodeCamp/freeCodeCamp"))

                .body("[1].name", is("EbookFoundation/free-programming-books"))
                .body("[1].description", is(":books: Freely available programming books"))
                .body("[1].starCount", is(278200))
                .body("[1].url", is("https://github.com/EbookFoundation/free-programming-books"));
    }

    @Test
    void shouldReturnEmptyResponse(RequestSpecification spec) {
        stubFor(requestMatching(request -> MatchResult.of(request.getUrl().contains("/search/repositories")))
                .willReturn(ok()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("empty_result.json")));

        spec.when()
                .get("/repositories")
                .prettyPeek()
                .then()
                .statusCode(200)
                .assertThat()
                .body("", empty());
    }

    @Test
    void shouldHandle403FromUpstreamWith403AsResult(RequestSpecification spec) {
        stubFor(requestMatching(request -> MatchResult.of(request.getUrl().contains("/search/repositories")))
                .willReturn(forbidden()));

        spec.when()
                .get("/repositories")
                .prettyPeek()
                .then()
                .statusCode(403)
                .assertThat()
                .body("message", is("API rate limit exceeded."));
    }

    @Test
    void shouldHandleErrorFromUpstreamWithOriginalCodeAsResult(RequestSpecification spec) {
        stubFor(requestMatching(request -> MatchResult.of(request.getUrl().contains("/search/repositories")))
                .willReturn(serviceUnavailable()));

        spec.when()
                .get("/repositories")
                .prettyPeek()
                .then()
                .statusCode(503)
                .assertThat()
                .body("message", is("Problem access upstream API with: Client 'github': Service Unavailable"));
    }

    @Test
    void shouldHandleIllegalArgumentAsBadRequest(RequestSpecification spec) {
        stubFor(requestMatching(request -> MatchResult.of(request.getUrl().contains("/search/repositories")))
                .willReturn(serviceUnavailable()));

        spec.when()
                .get("/repositories?limit=0")
                .prettyPeek()
                .then()
                .statusCode(400)
                .assertThat()
                .body("message", is("Invalid limit"));
    }
}