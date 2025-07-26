package com.example.simple.client.response;

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

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
