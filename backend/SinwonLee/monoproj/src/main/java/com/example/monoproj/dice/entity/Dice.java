package com.example.monoproj.dice.entity;

import com.example.monoproj.game.entity.Game;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@ToString
public class Dice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    public Dice(Integer number) {
        this.number = number;
    }

    public Dice() { }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
