package com.discoverrepository.api.http;

import com.discoverrepository.api.http.response.ErrorResponse;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces(MediaType.APPLICATION_JSON)
@Singleton
@Requires(classes = {HttpClientResponseException.class, ExceptionHandler.class})
public class HttpClientResponseExceptionHandler implements ExceptionHandler<HttpClientResponseException, HttpResponse<ErrorResponse>> {


    @Override
    public HttpResponse<ErrorResponse> handle(HttpRequest request, HttpClientResponseException exception) {
        if (HttpStatus.FORBIDDEN.equals(exception.getStatus())) {
            return HttpResponse.status(exception.getStatus())
                    .body(new ErrorResponse("API rate limit exceeded."));
        }

        return HttpResponse.status(exception.getStatus())
                .body(new ErrorResponse("Problem access upstream API with: " + exception.getMessage()));
    }
}
