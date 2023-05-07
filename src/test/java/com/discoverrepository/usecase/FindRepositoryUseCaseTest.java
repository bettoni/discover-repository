package com.discoverrepository.usecase;

import com.discoverrepository.domain.Repository;
import com.discoverrepository.gateway.RepositoryGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindRepositoryUseCaseTest {

    @Mock
    private RepositoryGateway repositoryGateway;
    @InjectMocks
    private FindRepositoryUseCase findRepositoryUseCase;

    @Test
    void shouldFetchRepositories() {
        Repository firstRepo = Repository.builder().build();
        Repository secondRepo = Repository.builder().build();
        when(repositoryGateway.fetchRepositories(any())).thenReturn(List.of(firstRepo, secondRepo));
        FindRepositoryFilter filter = FindRepositoryFilter.defaultFilter();

        assertThat(findRepositoryUseCase.findRepositories(filter))
                .containsOnly(firstRepo, secondRepo);
    }
}