package com.example.simple.controller;

import com.example.simple.client.BookClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    private final BookClient bookClient;

    public SimpleController(BookClient bookClient) {
        this.bookClient = bookClient;
    }

    @GetMapping("/")
    public String simpleHello() {
        return "Hello Simple Service";
    }

    @GetMapping("/call-book")
    public String callBookService() {
        return bookClient.hello();
    }
}
