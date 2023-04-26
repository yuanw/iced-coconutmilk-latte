package com.example.demo.service;

import com.example.demo.model.Person;
import com.example.demo.model.PersonWithCounter;
import com.example.demo.repository.PersonRepository;
import com.google.common.collect.Comparators;
import com.google.common.util.concurrent.AtomicLongMap;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class LookupService {
  private static final int TOP = 10;
  private final PersonRepository personRepository;
  private final PersonService personService;
  // should be replaced with redis/memcache
  private AtomicLongMap<String> counter = AtomicLongMap.create();

  public Mono<PersonWithCounter> lookup(String email) {
    Mono<Person> mono =
        personRepository
            .findById(email)
            .switchIfEmpty(
                personService
                    .lookup(email)
                    .flatMap(
                        person -> {
                          var p =
                              person.toBuilder()
                                  .email(Optional.ofNullable(person.getEmail()).orElse(email))
                                  .build();
                          return personRepository.save(p);
                        }));
    return mono.map(p -> PersonWithCounter.of(p, counter.incrementAndGet(p.getEmail())));
  }

  public Flux<String> popularity() {
    return Flux.fromIterable(
            counter.asMap().entrySet().stream()
                .collect(
                    Comparators.greatest(10, (e1, e2) -> e1.getValue().compareTo(e2.getValue()))))
        .map(e -> e.getKey());
  }
}
