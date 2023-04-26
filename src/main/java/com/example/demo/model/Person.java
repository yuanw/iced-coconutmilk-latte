package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder(toBuilder = true)
@Data
@Jacksonized
@Table(value = "person")
public class Person {

  @Id
  @Column("email")
  private String email;

  @Column("location")
  private String location;

  @Column("avatar")
  private String avatar;

  @Column("version")
  @Version
  private Long version;
}
