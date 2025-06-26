package com.example.monoproj.dice.controller.request_form;

import com.example.monoproj.dice.entity.Dice;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class DiceRollResultRequestForm {
    final private int number;
    final private String userToken;

    public Dice toDice() {
        Dice dice = new Dice();
        dice.setNumber(this.number);
        dice.setUserToken(this.userToken);
        return dice;
    }
}
