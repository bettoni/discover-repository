package com.discoverrepository.usecase;

import com.discoverrepository.domain.Repository;
import com.discoverrepository.gateway.RepositoryGateway;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Singleton
public class FindRepositoryUseCase {
    private final RepositoryGateway repositoryGateway;
    public List<Repository> findRepositories(FindRepositoryFilter filter) {
        return repositoryGateway.fetchRepositories(filter);
    }
}
