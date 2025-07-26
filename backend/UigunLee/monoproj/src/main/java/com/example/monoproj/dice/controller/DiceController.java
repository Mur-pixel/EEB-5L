package com.example.monoproj.dice.controller;

import com.example.monoproj.dice.controller.request_form.DiceRollResultRequestForm;
import com.example.monoproj.dice.service.DiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/dice")
public class DiceController {
    private final DiceService diceService;

    @PostMapping("/save-roll-result")
    public Boolean diceSaveRollResult(@RequestBody DiceRollResultRequestForm requestForm) {
        log.info("diceSaveRollResult() -> requestForm: {}", requestForm);
        return diceService.saveRollResult(requestForm);

    }
}