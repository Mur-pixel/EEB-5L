package com.example.monoproj.account.service;

import com.example.monoproj.account.entity.Account;
import com.example.monoproj.account.service.request.RegisterNormalAccountRequest;

public interface AccountService {
    Account createAccount(RegisterNormalAccountRequest request);
}
