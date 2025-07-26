package com.example.monoproj.dice.service;

import com.example.monoproj.dice.controller.request_form.DiceRollResultRequestForm;

public interface DiceService {
    Boolean saveRollResult(DiceRollResultRequestForm requestForm);
}
