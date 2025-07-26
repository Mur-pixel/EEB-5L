package com.example.monoproj.account_profile.service;

import com.example.monoproj.account.entity.Account;
import com.example.monoproj.account_profile.entity.AccountProfile;
import com.example.monoproj.account_profile.repository.AccountProfileRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountProfileServiceImpl implements AccountProfileService {
    final private AccountProfileRepository accountProfileRepository;

    @Override
    public void markTermsAccepted(Long accountId, boolean agreed) {
        // 1) 계정 ID로 프로필 조회.
        AccountProfile profile = accountProfileRepository.findWithAccountByEmail(String.valueOf(accountId))
                .orElseThrow(() -> new EntityNotFoundException("프로필 없음."));
        // 2) 약관 동의 여부를 프로필에 설정.
        profile.setTermsAccepted(agreed);
        // 3) 변경된 프로필을 DB에 저장.
        accountProfileRepository.save(profile);
    }

    @Override
    public AccountProfile createAccountProfile(Account account, String nickname, String email) {
        AccountProfile accountProfile = new AccountProfile(account, nickname, email);
        return this.accountProfileRepository.save(accountProfile);
    }

    @Override
    public Optional<AccountProfile> loadProfileByEmail(String email) {
        return accountProfileRepository.findWithAccountByEmail(email);
    }

    @Override
    public Optional<AccountProfile> loadProfileByAccountId(Long accountId) {
        return accountProfileRepository.findWithAccountByAccountId(accountId);
    }
}
