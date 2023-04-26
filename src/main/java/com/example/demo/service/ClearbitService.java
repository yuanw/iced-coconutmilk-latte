package com.example.demo.service;

import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.ServerException;
import com.example.demo.exception.UnprocessableException;
import com.example.demo.model.Person;
import io.netty.handler.logging.LogLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;
import reactor.util.retry.Retry;

@Component
@Slf4j
public class ClearbitService implements PersonService {
  private final String apiKey;
  private final String host;

  private WebClient webClient;

  public ClearbitService(
      @Value("${clearbit.apikey}") String apiKey, @Value("${clearbit.host}") String host) {
    this.apiKey = apiKey;
    this.host = host;
    WebClient.Builder builder = WebClient.builder().baseUrl(this.host);

    if (log.isDebugEnabled()) {
      builder.clientConnector(
          new ReactorClientHttpConnector(
              HttpClient.create()
                  .wiretap(
                      this.getClass().getCanonicalName(),
                      LogLevel.DEBUG,
                      AdvancedByteBufFormat.TEXTUAL)));
    }
    webClient = builder.build();
  }

  public Mono<Person> lookup(String email) {
    return webClient
        .get()
        .uri(host + "/v2/people/find?email=" + email)
        .headers(headers -> headers.setBearerAuth(apiKey))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        // basic error handling, didn't test around rate limited
        .onStatus(sc -> sc.value() == 404, resp -> Mono.error(new NotFoundException()))
        .onStatus(sc -> sc.value() == 422, resp -> Mono.error(new UnprocessableException()))
        .onStatus(HttpStatusCode::is5xxServerError, resp -> Mono.error(new ServerException()))
        .bodyToMono(Person.class)
        .retryWhen(Retry.max(2).filter(ServerException.class::isInstance));
  }
}
