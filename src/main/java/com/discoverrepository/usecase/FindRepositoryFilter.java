package com.discoverrepository.usecase;

import io.micronaut.core.annotation.Nullable;

import java.time.LocalDate;


public record FindRepositoryFilter(@Nullable LocalDate creationDate,
                                   @Nullable String language,
                                   @Nullable Integer limit) {
    public FindRepositoryFilter(@Nullable LocalDate creationDate,
                                @Nullable String language,
                                @Nullable Integer limit) {
        this.creationDate = creationDate;
        this.language = language;
        this.limit = limit == null ? 10 : limit;
    }

    public static FindRepositoryFilter defaultFilter() {
        return new FindRepositoryFilter(null, null, null);
    }
}
