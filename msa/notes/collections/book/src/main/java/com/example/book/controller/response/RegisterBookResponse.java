package com.example.book.controller.response;

import com.example.book.entity.Book;
import lombok.Getter;

@Getter
public class RegisterBookResponse {
    private Long id;
    private String title;
    private String content;
    private String author;
    private String isbn;

    public RegisterBookResponse() {
    }

    public RegisterBookResponse(Long id, String title, String content, String author, String isbn) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.isbn = isbn;
    }

    public static RegisterBookResponse from(Book book) {
        return new RegisterBookResponse(
                book.getId(),
                book.getTitle(),
                book.getContent(),
                book.getAuthor(),
                book.getIsbn()
        );
    }
}
