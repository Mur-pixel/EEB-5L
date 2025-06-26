package com.example.monoproj.dice.service;

import com.example.monoproj.dice.controller.request_form.DiceRollResultRequestForm;
import com.example.monoproj.dice.entity.Dice;
import com.example.monoproj.dice.repository.DiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiceServiceImpl implements DiceService {
    final private DiceRepository diceRepository;

    @Override
    public Boolean saveRollResult(DiceRollResultRequestForm requestForm) {
        log.info("saveRollResult(): " + requestForm);
        Dice savedDice = diceRepository.save(requestForm.toDice());
        return savedDice != null;
    }
}
