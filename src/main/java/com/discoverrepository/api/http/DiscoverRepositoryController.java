package com.discoverrepository.api.http;

import com.discoverrepository.api.http.response.RepositoryResponse;
import com.discoverrepository.usecase.FindRepositoryFilter;
import com.discoverrepository.usecase.FindRepositoryUseCase;
import io.micronaut.core.convert.format.Format;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller("/repositories")
@RequiredArgsConstructor
public class DiscoverRepositoryController {

    private final FindRepositoryUseCase findRepositoryUseCase;

    @Get(uri = "/", produces = "application/json")
    public List<RepositoryResponse> index(@QueryValue @Format("yyyy-MM-dd") Optional<LocalDate> createdAfter,
                                          @QueryValue Optional<String> language,
                                          @QueryValue Optional<Integer> limit) {
        return findRepositoryUseCase.findRepositories(createFilter(createdAfter, language, limit)).stream()
                .map(RepositoryResponse::fromDomain)
                .collect(Collectors.toList());
    }

    private static FindRepositoryFilter createFilter(Optional<LocalDate> createdAfter, Optional<String> language, Optional<Integer> limit) {
        return new FindRepositoryFilter(
                createdAfter.orElse(null),
                language.orElse(null),
                limit.orElse(null)
        );
    }
}