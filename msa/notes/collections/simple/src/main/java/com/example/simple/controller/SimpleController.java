package com.example.simple.controller;

import com.example.simple.client.AccountClient;
import com.example.simple.client.BookClient;
import com.example.simple.client.request.RegisterAccountRequest;
import com.example.simple.client.request.RegisterBookRequest;
import com.example.simple.client.response.BookResponse;
import com.example.simple.client.response.RegisterAccountResponse;
import com.example.simple.client.response.RegisterBookResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Book;

@RestController
public class SimpleController {

    private final BookClient bookClient;
    private final AccountClient accountClient;

    public SimpleController(BookClient bookClient, AccountClient accountClient) {
        this.bookClient = bookClient;
        this.accountClient = accountClient;
    }

    @GetMapping("/")
    public String simpleHello() {
        return "Hello Simple Service";
    }
    
    // path를 통해 실제 URI 경로를 지정합니다.
    // produces를 통해서 이 API가 JSON을 리턴할 것임을 명시해야 합니다.
    // ResponseEntity<리턴타입> 형태는 원래 @Controller를 사용할 때 쓰는 표현입니다 (JSON 리턴을 위해)
    @GetMapping(path = "/call-book-test", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResponse> callBookTest() {
        // @FeignClient로 구성했던 외부 서비스 API
        // 실제로 어제의 구성도 이와 동일하였으나 Lombok을 썻음
        // 또한 이게 잘 될 때가 있고 잘 안되는 경우가 있어서
        // 안전하게 자동완성(Alt + Insert)를 사용하는 방식으로
        // Getter와 Constructor를 추가하는 것을 권장합니다.
        BookResponse result = bookClient.testBook();

        return ResponseEntity.ok(result);
    }

    @GetMapping(path = "/call-book-test2",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisterBookResponse> callBookTest2() {
        RegisterBookResponse result = bookClient.testBook2();

        return ResponseEntity.ok(result);
    }

    @PostMapping("/call-book-register")
    public RegisterBookResponse register() {
        RegisterBookRequest request = new RegisterBookRequest(
                "책제목", "책내용", "책저자", "책번호");
        return bookClient.register(request);
    }

    @PostMapping("/call-account-register")
    public RegisterAccountResponse registerAccount() {
        RegisterAccountRequest request = new RegisterAccountRequest(
                "계정아이디", "계정비밀번호");
        return accountClient.register(request);
    }
}
