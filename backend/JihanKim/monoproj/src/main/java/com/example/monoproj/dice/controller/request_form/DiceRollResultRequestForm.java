package com.example.monoproj.dice.controller.request_form;

import com.example.monoproj.dice.entity.Dice;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class DiceRollResultRequestForm {
    final private int number;

    public Dice toDice() {
        return new Dice(number);
    }
}
