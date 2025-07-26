package com.example.monoproj.account_profile.service;


import com.example.monoproj.account.entity.Account;
import com.example.monoproj.account_profile.entity.AccountProfile;

import java.util.Optional;

public interface AccountProfileService {
    AccountProfile createAccountProfile(Account account, String nickname, String email);
    Optional<AccountProfile> loadProfileByEmail(String email);
    Optional<AccountProfile> loadProfileByAccountId(Long accountId);
    void markTermsAccepted(Long accountId, boolean agreed);
}
