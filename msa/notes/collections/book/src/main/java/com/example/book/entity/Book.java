package com.example.book.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String author;
    private String isbn;

    private Long accountId;

    public Book(String title, String content, String author, String isbn) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.isbn = isbn;
    }

    public Book(String title, String content, String author, String isbn, Long accountId) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.isbn = isbn;
        this.accountId = accountId;
    }
}
