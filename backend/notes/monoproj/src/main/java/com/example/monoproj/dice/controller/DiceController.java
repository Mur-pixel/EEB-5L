package com.example.monoproj.dice.controller;

import com.example.monoproj.dice.controller.request_form.DiceRollResultRequestForm;
import com.example.monoproj.dice.service.DiceService;
import com.example.monoproj.redis_cache.service.RedisCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/dice")
public class DiceController {
    private final DiceService diceService;
    private final RedisCacheService redisCacheService;

    @PostMapping("/save-roll-result")
    public Boolean diceSaveRollResult(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody DiceRollResultRequestForm requestForm) {
        log.info("diceSaveRollResult() -> requestForm: {}", requestForm);
        log.info("authorizationHeader -> {}", authorizationHeader);

        String token = authorizationHeader.replace("Bearer ", "").trim();
        Long accountId = redisCacheService.getValueByKey(token, Long.class);
        log.info("accountId -> {}", accountId);

        return diceService.saveRollResult(
                requestForm.toDiceRollResultRequest(),
                requestForm.toGameFindRequest(),
                accountId);
    }
}
