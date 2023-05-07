package com.discoverrepository.gateway.impl.github.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GithubRepository {
    private String name;
    private String fullName;
    private String description;
    @JsonProperty("private")
    private Boolean privateRepo;
    private String htmlUrl;
    private String url;
    private String language;
    private Integer stargazersCount;
    private OffsetDateTime createdAt;

}
