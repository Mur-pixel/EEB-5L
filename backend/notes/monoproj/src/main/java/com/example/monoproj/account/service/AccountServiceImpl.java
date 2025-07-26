package com.example.monoproj.account.service;

import com.example.monoproj.account.entity.*;
import com.example.monoproj.account.repository.AccountLoginTypeRepository;
import com.example.monoproj.account.repository.AccountRepository;
import com.example.monoproj.account.repository.AccountRoleTypeRepository;
import com.example.monoproj.account.service.request.RegisterNormalAccountRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    final private AccountRepository accountRepository;
    final private AccountRoleTypeRepository accountRoleTypeRepository;
    final private AccountLoginTypeRepository accountLoginTypeRepository;

    @Override
    public Account createAccount(RegisterNormalAccountRequest request) {
        AccountRoleType accountRoleType = accountRoleTypeRepository.findByRoleType(RoleType.NORMAL)
                .orElseThrow(() -> new IllegalStateException("RoleType.NORMAL 이 DB에 없습니다."));

        // 2. 로그인 타입 찾기
        LoginType loginType = request.getLoginType();
        AccountLoginType accountLoginType = accountLoginTypeRepository.findByLoginType(loginType)
                .orElseThrow(() -> new IllegalStateException("LoginType.%s 이 DB에 없습니다.".formatted(loginType)));

        // 3. Account 생성 및 저장
        Account account = new Account(accountRoleType, accountLoginType);
        return accountRepository.save(account);
    }
}
