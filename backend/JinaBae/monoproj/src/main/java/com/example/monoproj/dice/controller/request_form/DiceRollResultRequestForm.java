package com.example.monoproj.dice.controller.request_form;

import com.example.monoproj.dice.entity.Dice;
import com.example.monoproj.dice.service.request.DiceRollResultRequest;
import com.example.monoproj.game.service.request.GameFindRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class DiceRollResultRequestForm {
    final private Long gameId;
    final private int number;

    public DiceRollResultRequest toDiceRollResultRequest() {
        return new DiceRollResultRequest(number);
    }

    public GameFindRequest toGameFindRequest() {
        return new GameFindRequest(gameId);
    }
}
