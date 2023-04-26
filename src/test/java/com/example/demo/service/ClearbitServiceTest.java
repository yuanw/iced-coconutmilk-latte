package com.example.demo.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application.properties")
public class ClearbitServiceTest {

  @Autowired
  @Value("${clearbit.apikey}")
  String apiKey;

  @Autowired
  @Value("${clearbit.host}")
  String host;

  ClearbitService service;

  @BeforeEach
  void setup() {
    service = new ClearbitService(apiKey, host);
  }

  @Test
  void contextLoads() {
    StepVerifier.create(service.lookup("test@example.com").log())
        .assertNext(nodeDocs -> Assertions.assertThat(nodeDocs).isNotNull())
        .verifyComplete();
  }
}
