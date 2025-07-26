package com.example.monoproj.account_profile.service;

import com.example.monoproj.account.entity.Account;
import com.example.monoproj.account.entity.LoginType;
import com.example.monoproj.account_profile.entity.AccountProfile;
import com.example.monoproj.account_profile.repository.AccountProfileRepository;
import com.example.monoproj.account_profile.service.request.RegisterAccountProfileRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountProfileServiceImpl implements AccountProfileService {
    final private AccountProfileRepository accountProfileRepository;

    @Override
    public AccountProfile createAccountProfile(Account account, RegisterAccountProfileRequest request) {
        String nickname = request.getNickname();
        String email = request.getEmail();

        AccountProfile accountProfile = new AccountProfile(account, nickname, email);
        return this.accountProfileRepository.save(accountProfile);
    }

    @Override
    public Optional<AccountProfile> loadProfileByEmail(String email) {
        return accountProfileRepository.findWithAccountByEmail(email);
    }

    @Override
    public Optional<AccountProfile> loadProfileByEmailAndLoginType(String email, LoginType loginType) {
        return accountProfileRepository.findWithAccountByEmailAndLoginType(email, loginType);
    }
}
