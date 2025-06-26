package com.example.monoproj.dice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    public Dice() {
    }

    public Dice(Integer number) {
        this.number = number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
