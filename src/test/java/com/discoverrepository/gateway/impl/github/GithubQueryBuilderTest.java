package com.discoverrepository.gateway.impl.github;

import com.discoverrepository.usecase.FindRepositoryFilter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class GithubQueryBuilderTest {

    @Test
    void shouldReturnDefaultQueryWhenNoFilter () {
        FindRepositoryFilter filter = FindRepositoryFilter.defaultFilter();

        String result = GithubQueryBuilder.createQuery(filter);

        assertThat(result).isEqualTo("stars:>=1");
    }

    @Test
    void shouldReturnDefaultQueryPlusCreatedDateFilter () {
        FindRepositoryFilter filter = new FindRepositoryFilter(LocalDate.parse("2023-01-15"), null, null);

        String result = GithubQueryBuilder.createQuery(filter);

        assertThat(result).isEqualTo("stars:>=1 created:>=2023-01-15");
    }

    @Test
    void shouldReturnDefaultQueryPlusLanguageFilter () {
        FindRepositoryFilter filter = new FindRepositoryFilter(null, "cobol", null);

        String result = GithubQueryBuilder.createQuery(filter);

        assertThat(result).isEqualTo("stars:>=1 language:cobol");
    }

    @Test
    void shouldReturnQueryWithAllFilters () {
        FindRepositoryFilter filter = new FindRepositoryFilter(LocalDate.parse("2023-01-15"), "cobol", null);

        String result = GithubQueryBuilder.createQuery(filter);

        assertThat(result).isEqualTo("stars:>=1 created:>=2023-01-15 language:cobol");
    }
}