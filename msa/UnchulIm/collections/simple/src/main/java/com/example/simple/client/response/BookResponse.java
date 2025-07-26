package com.example.simple.client.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

// MSA 환경에서 생각보다 Lombok이 요상하게 동작할 수 있음.
// 그러므로 실제 Getter를 Alt + Insert로 직접 구성하고
// Constructor 도 Alt + Insert를 눌러서 아래와 같이
// NoArgsConstructor와 AllArgsConstructor를 용도에 맞게 구성해주세요.
public class BookResponse {
    private String title;
    private String content;
    private String author;
    private String isbn;

    public BookResponse() {
    }

    public BookResponse(String title, String content, String author, String isbn) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}

