package com.discoverrepository.gateway.impl.github;

import com.discoverrepository.usecase.FindRepositoryFilter;
import io.micronaut.core.util.StringUtils;
import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class GithubQueryBuilder {

    public static String createQuery(FindRepositoryFilter filter) {
        StringBuilder sb = new StringBuilder();
        sb.append("stars:>=1");

        if (Objects.nonNull(filter.creationDate())) {
            sb.append(" created:>=");
            sb.append(filter.creationDate());
        }

        if (StringUtils.isNotEmpty(filter.language())) {
            sb.append(" language:");
            sb.append(filter.language());
        }

        return sb.toString();
    }
}
