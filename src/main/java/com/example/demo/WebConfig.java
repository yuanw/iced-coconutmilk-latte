package com.example.demo;

import com.example.demo.controller.LookupController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
class WebConfig {

  @Bean
  public RouterFunction<ServerResponse> routes(LookupController lookupController) {
    return RouterFunctions.route(
            RequestPredicates.GET("/lookup")
                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
            lookupController::lookup)
        .andRoute(RequestPredicates.GET("/pop"), lookupController::populartiy);
  }
}
