package com.example.monoproj.dice.service;

import com.example.monoproj.dice.service.request.DiceRollResultRequest;
import com.example.monoproj.game.service.request.GameFindRequest;

public interface DiceService {
    Boolean saveRollResult(
            DiceRollResultRequest request,
            GameFindRequest gameFindRequest,
            Long accountId);
}
