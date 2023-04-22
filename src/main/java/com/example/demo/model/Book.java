package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "book")
public class Book {

  @Id
  @Column("id")
  private UUID id;

  @Column("isbn")
  private String isbn;

  @Column("title")
  private String title;

  @Column("description")
  private String description;

  @Column("created_at")
  @CreatedDate
  private LocalDateTime createdAt;

  @Column("updated_at")
  @LastModifiedDate
  private LocalDateTime updatedAt;

  @Column("version")
  @Version
  @Builder.Default
  private Long version = 0L;
}
