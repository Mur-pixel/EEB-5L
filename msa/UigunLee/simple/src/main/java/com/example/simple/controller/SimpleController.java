package com.example.simple.controller;

import com.example.simple.client.BookClient;
import com.example.simple.client.response.BookResponse;
import com.example.simple.client.response.RegisterBookResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    // path를 통해 실제 URI 경로를 지정합니다.
    // produces를 통해서 이 API가 JSON을 리턴할 것임을 명시해야 합니다.
    // ResponseEntity<리턴타입> 형태는 원래 @Controller를 사용할 때 쓰는 표현입니다. (JSON 리턴을 위해)
    @GetMapping(path = "/call-book-test", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResponse> callBookTest() {
        BookResponse result = bookClient.testBook();

        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/call-book-test2", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisterBookResponse> callBookTest2() {
        RegisterBookResponse result = bookClient.testBook2();

        return ResponseEntity.ok(result);
    }
}
