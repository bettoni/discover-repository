package com.discoverrepository.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
@Builder
public class Repository {
    private String name;
    private String description;
    private Integer starCount;
    private String url;
    private String language;
    private OffsetDateTime createdAt;

}
