package com.example.demo.repository;

import com.example.demo.model.Book;
import java.util.UUID;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface BookRepository extends R2dbcRepository<Book, UUID> {

  @Query("SELECT * FROM book where title like :title")
  public Flux<Book> findByTitleContains(String title);
}
