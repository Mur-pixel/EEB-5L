package com.example.monoproj.account.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "role_type_id", nullable = false)
    private AccountRoleType roleType;

    public Account() {
    }

    public Account(AccountRoleType roleType) {
        this.roleType = roleType;
    }

    public AccountRoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(AccountRoleType roleType) {
        this.roleType = roleType;
    }
}
