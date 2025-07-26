package com.example.monoproj.account_profile.repository;

import com.example.monoproj.account.entity.Account;
import com.example.monoproj.account_profile.entity.AccountProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountProfileRepository extends JpaRepository<AccountProfile, Long> {
    Optional<AccountProfile> findByAccount(Account account);

    @Query("SELECT ap FROM AccountProfile ap JOIN FETCH ap.account WHERE ap.email = :email")
    Optional<AccountProfile> findWithAccountByEmail(@Param("email") String email);
    //JPQL
    @Query("""
        SELECT ap
          FROM AccountProfile ap
          JOIN FETCH ap.account
         WHERE ap.account.id = :accountId
""")
    //JPQL은 데이터베이스 칼럼명이 아니라 엔티티의 “속성 이름” 으로 매핑
    Optional<AccountProfile> findWithAccountByAccountId(@Param("accountId") Long accountId);


}
