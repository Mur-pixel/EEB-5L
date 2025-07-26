package com.example.monoproj.account_profile.entity;

import com.example.monoproj.account.entity.Account;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "account_profile")
public class AccountProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nickname", length = 32, unique = true, nullable = false)
    private String nickname;

    @Column(name = "email", length = 32, unique = true, nullable = false)
    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    // 이 엔티티의 외래키 컬럼을 지정.
    // 데이터베이스 테이블의 account_id 컬럼과 매핑.
    // 참조 대상 테이블의 id 컬럼을 외래키로 사용.
    // null 값 저장을 허용하지 않음.
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private Account account;

    public AccountProfile() {
    }

    public AccountProfile(Account account, String nickname, String email) {
        this.account = account;
        this.nickname = nickname;
        this.email = email;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setTermsAccepted(boolean agreed) {
    }
}

