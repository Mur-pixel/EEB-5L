package com.example.monoproj.account_profile.service;


import com.example.monoproj.account.entity.Account;
import com.example.monoproj.account.entity.LoginType;
import com.example.monoproj.account_profile.entity.AccountProfile;
import com.example.monoproj.account_profile.service.request.RegisterAccountProfileRequest;

import java.util.Optional;

public interface AccountProfileService {
    AccountProfile createAccountProfile(Account account, RegisterAccountProfileRequest request);
    Optional<AccountProfile> loadProfileByEmail(String email);
    Optional<AccountProfile> loadProfileByEmailAndLoginType(String email, LoginType loginType);
}
