package com.example.book.controller.request;

import com.example.book.entity.Book;
import lombok.*;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookRequest {
    private Long bookId;
    private String title;
    private String content;
}
