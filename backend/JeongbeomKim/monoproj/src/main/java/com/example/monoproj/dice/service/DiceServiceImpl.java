package com.example.monoproj.dice.service;

import com.example.monoproj.dice.controller.request_form.DiceRollResultRequestForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiceServiceImpl implements DiceService {

    @Override
    public Boolean saveRollResult(DiceRollResultRequestForm requestForm) {
        return null;
    }
}