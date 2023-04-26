package com.example.demo.model;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Builder(toBuilder = true)
@Value
@Jacksonized
public class PersonWithCounter {
  private String email;

  private String location;

  private String avatar;

  private Long counter;

  public static PersonWithCounter of(Person person, Long counter) {
    return PersonWithCounter.builder()
        .email(person.getEmail())
        .location(person.getLocation())
        .avatar(person.getAvatar())
        .counter(counter)
        .build();
  }
}
