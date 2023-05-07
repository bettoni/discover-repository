package com.discoverrepository.gateway;

import com.discoverrepository.domain.Repository;
import com.discoverrepository.usecase.FindRepositoryFilter;

import java.util.List;

public interface RepositoryGateway {
    List<Repository> fetchRepositories(FindRepositoryFilter filter);
}
