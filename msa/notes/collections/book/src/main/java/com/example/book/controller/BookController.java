package com.example.book.controller;

import com.example.book.client.AccountClient;
import com.example.book.controller.request.RegisterBookRequest;
import com.example.book.controller.request.RegisterBookWithAuthorizationRequest;
import com.example.book.controller.request.UpdateBookRequest;
import com.example.book.controller.response.*;
import com.example.book.entity.Book;
import com.example.book.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    private final AccountClient accountClient;

    public BookController(AccountClient accountClient) {
        this.accountClient = accountClient;
    }

    @GetMapping("/test")
    public String test() {
        log.info("return String from test()");
        Book book =  new Book("제목", "내용", "저자", "isbn");
        log.info("book = {}", book);
        return "Book Service";
    }

    @GetMapping("/test-book")
    public BookResponse testBook() {
        log.info("return Book from testBook()");
        BookResponse bookResponse =  new BookResponse("제목", "내용", "저자", "isbn");
        log.info("bookResponse = {}", bookResponse);
        return bookResponse;
    }

    @GetMapping("/test-book2")
    public RegisterBookResponse testBook2() {
        log.info("return Book from testBook2()");
        Book createdBook =  new Book("제목", "내용", "저자", "isbn");
        return RegisterBookResponse.from(createdBook);
    }

    @PostMapping("/register")
    public RegisterBookResponse register(@RequestBody RegisterBookRequest request) {
        log.info("register() -> request = {}", request);
        Book book = bookRepository.save(request.toBook());
        return RegisterBookResponse.from(book);
    }

    @PostMapping("/publication")
    public RegisterBookWithAuthorizationResponse register(
            @RequestHeader("Authorization") String token,
            @RequestBody RegisterBookWithAuthorizationRequest request) {

        log.info("register() -> request = {}", request);

        // userToken 획득
        String pureToken = extractToken(token);
        // FeignClient를 통해 account 서비스에 accountId 요청
        IdAccountResponse response = accountClient.getAccountId("Bearer " + pureToken);
        Long accountId = response.getAccountId();

        log.info("accountId = {}", accountId);

        Book requestedBook = request.toBook(accountId);
        Book registeredBook = bookRepository.save(requestedBook);
        return RegisterBookWithAuthorizationResponse.from(registeredBook);
    }

    @PostMapping("/update")
    public UpdateBookResponse register(
            @RequestHeader("Authorization") String token,
            @RequestBody UpdateBookRequest request) {

        log.info("register() -> request = {}", request);

        String pureToken = extractToken(token);
        IdAccountResponse response = accountClient.getAccountId("Bearer " + pureToken);
        Long accountId = response.getAccountId();

        Long requestedBookId = request.getBookId();
        Book foundBook = bookRepository.findById(requestedBookId)
                .orElseThrow(() -> new RuntimeException("이런 책은 존재하지 않습니다: " + requestedBookId));

        if (!foundBook.getAccountId().equals(accountId)) {
            throw new RuntimeException("책을 등록한 사람이 아닙니다.");
        }

        foundBook.setTitle(request.getTitle());
        foundBook.setContent(request.getContent());

        Book updatedBook = bookRepository.save(foundBook);
        return UpdateBookResponse.from(updatedBook);
    }

    private String extractToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return token;
    }
}
