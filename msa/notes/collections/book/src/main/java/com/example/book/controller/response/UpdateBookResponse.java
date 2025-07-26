package com.example.book.controller.response;

import com.example.book.entity.Book;
import lombok.Getter;

@Getter
public class UpdateBookResponse {
    private Long id;
    private String title;
    private String content;
    private String author;
    private String isbn;

    private Long accountId;

    public UpdateBookResponse() {
    }

    public UpdateBookResponse(Long id, String title, String content, String author, String isbn, Long accountId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.isbn = isbn;

        this.accountId = accountId;
    }

    public static UpdateBookResponse from(Book book) {
        return new UpdateBookResponse(
                book.getId(),
                book.getTitle(),
                book.getContent(),
                book.getAuthor(),
                book.getIsbn(),
                book.getAccountId()
        );
    }
}
