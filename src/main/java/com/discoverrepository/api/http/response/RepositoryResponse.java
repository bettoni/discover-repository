package com.discoverrepository.api.http.response;

import com.discoverrepository.domain.Repository;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class RepositoryResponse {
    private String name;
    private String description;
    private Integer starCount;
    private String language;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime createdAt;
    private String url;

    public static RepositoryResponse fromDomain(Repository domainRepository) {
        return RepositoryResponse.builder()
                .name(domainRepository.getName())
                .description(domainRepository.getDescription())
                .url(domainRepository.getUrl())
                .starCount(domainRepository.getStarCount())
                .language(domainRepository.getLanguage())
                .createdAt(domainRepository.getCreatedAt())
                .build();
    }
}
