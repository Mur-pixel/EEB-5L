package com.example.monoproj.account.service;

import com.example.monoproj.account.entity.Account;
import com.example.monoproj.account.entity.AccountRoleType;
import com.example.monoproj.account.entity.RoleType;
import com.example.monoproj.account.repository.AccountRepository;
import com.example.monoproj.account.repository.AccountRoleTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    final private AccountRepository accountRepository;
    final private AccountRoleTypeRepository accountRoleTypeRepository;

    @Override
    public Account createAccount() {
        AccountRoleType accountRoleType = new AccountRoleType(RoleType.NORMAL);
        AccountRoleType createdAccountRoleType = this.accountRoleTypeRepository.save(accountRoleType);

        Account account = new Account(createdAccountRoleType);
        return this.accountRepository.save(account);
    }
}
