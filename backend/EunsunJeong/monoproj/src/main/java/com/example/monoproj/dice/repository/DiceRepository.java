package com.example.monoproj.dice.repository;

import com.example.monoproj.dice.entity.Dice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiceRepository extends JpaRepository<Dice, Integer> {
}
