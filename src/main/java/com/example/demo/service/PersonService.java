package com.example.demo.service;

import com.example.demo.model.Person;
import reactor.core.publisher.Mono;

public interface PersonService {
  Mono<Person> lookup(String email);
}
