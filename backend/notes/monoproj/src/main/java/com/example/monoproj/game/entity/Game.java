package com.example.monoproj.game.entity;

import com.example.monoproj.account.entity.Account;
import com.example.monoproj.dice.entity.Dice;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dice> diceList = new ArrayList<>();

    @CreatedDate
    private LocalDateTime startedAt;

    public void setAccount(Account account) {
        this.account = account;
    }

    public void addDice(Dice dice) {
        this.diceList.add(dice);
        dice.setGame(this);
    }
}
