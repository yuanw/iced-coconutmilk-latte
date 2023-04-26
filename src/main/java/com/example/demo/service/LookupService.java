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
  // attempt to avoid rows lock with top N popularity request
  // should be replaced with redis/memcache to keep stateless
  // limit with host memory size
  private AtomicLongMap<String> counter = AtomicLongMap.create();

  public Mono<PersonWithCounter> lookup(String email) {
    Mono<Person> mono =
        personRepository
            .findById(email) // check database
            .switchIfEmpty( // if missing fetch external service
                personService
                    .lookup(email)
                    .flatMap(
                        person -> {
                          var p =
                              person.toBuilder() // response might miss email
                                  .email(Optional.ofNullable(person.getEmail()).orElse(email))
                                  .build();
                          return personRepository.save(p); // save in database
                        }));
    return mono.map(
        p -> PersonWithCounter.of(p, counter.incrementAndGet(p.getEmail()))); // merge with counter
  }

  public Flux<String> popularity() {
    return Flux.fromIterable(
            counter.asMap().entrySet().stream()
                .collect(
                    // Top N entries, should be linear, can be replaced with ETL or stream process
                    // with cache
                    Comparators.greatest(TOP, (e1, e2) -> e1.getValue().compareTo(e2.getValue()))))
        .map(e -> e.getKey());
  }
}
