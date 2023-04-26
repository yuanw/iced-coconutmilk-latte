package com.example.demo.controller;

import static org.springframework.web.reactive.function.server.ServerResponse.*;

import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.UnprocessableException;
import com.example.demo.service.LookupService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class LookupController {
  private final LookupService lookService;
  private static final ParameterizedTypeReference<List<String>> LIST_TYPE_REF =
      new ParameterizedTypeReference<List<String>>() {};

  public Mono<ServerResponse> lookup(ServerRequest req) {
    String email = req.queryParam("email").get();
    return lookService
        .lookup(email)
        .flatMap(p -> ok().bodyValue(p))
        .onErrorResume(
            NotFoundException.class,
            e ->
                notFound()
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build())
        .onErrorResume(
            UnprocessableException.class,
            e -> unprocessableEntity().bodyValue(Map.of("error", "unprocessable email")))
        .onErrorResume(e -> true, e -> badRequest().bodyValue(Map.of("error", "unknown error")));
  }

  public Mono<ServerResponse> populartiy(ServerRequest req) {
    return ok().body(lookService.popularity().collectList(), LIST_TYPE_REF);
  }
}
