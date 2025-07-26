package com.example.monoproj.dice.service.request;

import com.example.monoproj.dice.entity.Dice;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class DiceRollResultRequest {
    final private int number;

    public Dice toDice() {
        return new Dice(number);
    }
}
