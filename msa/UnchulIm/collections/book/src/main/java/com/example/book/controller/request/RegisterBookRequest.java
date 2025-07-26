package com.example.book.controller.request;

import com.example.book.entity.Book;
import lombok.*;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterBookRequest {
    private String title;
    private String content;
    private String author;
    private String isbn;

    public Book toBook() {
        return new Book(title, content, author, isbn);
    }
}
