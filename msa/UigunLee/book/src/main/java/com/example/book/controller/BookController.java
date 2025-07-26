package com.example.book.controller;

import com.example.book.controller.reponse.BookResponse;
import com.example.book.controller.reponse.RegisterBookResponse;
import com.example.book.controller.request.RegisterBookRequest;
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
        RegisterBookResponse registerBookResponse =  new RegisterBookResponse("제목", "내용", "저자", "isbn");
        log.info("registerBookResponse = {}", registerBookResponse);
        return registerBookResponse;
    }

    @PostMapping("/register")
    public Book register(@RequestBody RegisterBookRequest request) {
        log.info("register() -> request = {}", request);

        return bookRepository.save(request.toBook());
    }
}
