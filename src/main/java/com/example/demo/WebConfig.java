package com.example.demo;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.example.demo.controller.BookController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
class WebConfig {

  @Bean
  public RouterFunction<ServerResponse> routes(BookController bookHandler) {
    return route()
        .path(
            "/books",
            () -> route().nest(path(""), () -> route().GET("", bookHandler::all).build()).build())
        .build();
  }
}
