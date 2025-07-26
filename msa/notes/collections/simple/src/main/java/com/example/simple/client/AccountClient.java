package com.example.simple.client;

import com.example.simple.client.request.RegisterAccountRequest;
import com.example.simple.client.response.RegisterAccountResponse;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "account")
public interface AccountClient {

    @PostMapping("/account/register")
    RegisterAccountResponse register(@RequestBody RegisterAccountRequest request);
}
