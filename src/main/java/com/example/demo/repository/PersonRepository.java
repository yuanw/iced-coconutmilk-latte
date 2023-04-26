package com.example.demo.repository;

import com.example.demo.model.Person;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface PersonRepository extends R2dbcRepository<Person, String> {}
