package com.example.demo.controller;

import com.example.demo.model.Book;

import com.example.demo.repository.BookRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
public class BookController {
  private final BookRepository posts;

     public BookController(BookRepository posts) {
        this.posts = posts;
    }

    public Mono<ServerResponse> all(ServerRequest req) {
        return ok().body(this.posts.findAll(), Book.class);
    }
}
