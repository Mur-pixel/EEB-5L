package com.example.simple.client;

import com.example.simple.client.request.RegisterBookRequest;
import com.example.simple.client.response.BookResponse;
import com.example.simple.client.response.RegisterBookResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "book")
public interface BookClient {

    @GetMapping("/book/test")
    String test();

    @GetMapping("/book/test-book")
    BookResponse testBook();

    @GetMapping("/book/test-book2")
    RegisterBookResponse testBook2();

    @PostMapping("/book/register")
    RegisterBookResponse register(@RequestBody RegisterBookRequest request);
}
