package com.discoverrepository;

import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@MicronautTest
class DiscoverRepositoryTest {

    @Inject
    EmbeddedApplication<?> application;

    @Test
    void shouldStartAppContext() {
        Assertions.assertTrue(application.isRunning());
    }
}
