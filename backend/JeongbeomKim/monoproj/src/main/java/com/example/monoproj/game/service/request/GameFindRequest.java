package com.example.monoproj.game.service.request;

import com.example.monoproj.dice.entity.Dice;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class GameFindRequest {
    final private Long gameId;
}
