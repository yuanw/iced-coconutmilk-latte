package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.demo.controller.BookController;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
class WebConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(
            BookController bookHandler) {
        return route()
                .path("/books", () -> route()
                        .nest(
                                path(""),
                                () -> route()
                                        .GET("", bookHandler::all)
                                        .build()
                        )
                                               .build()
                ).build();
    }
}
