package com.example.monoproj.dice.controller.request_form;

import com.example.monoproj.dice.entity.Dice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceRollResultRequestFormTest {

    @Test
    void toDice_shouldReturnDiceWithCorrectNumber() {
        // given
        int inputNumber = 4;
        DiceRollResultRequestForm form = new DiceRollResultRequestForm(inputNumber);

        // when
        Dice dice = form.toDice();

        // then
        assertNotNull(dice, "Dice 객체는 null이 아니어야 합니다.");
        assertEquals(inputNumber, dice.getNumber(), "Dice의 number 값이 입력값과 일치해야 합니다.");
        // id는 아직 DB에 저장되지 않았기 때문에 null인 것이 정상입니다.
        assertNull(dice.getId(), "id는 아직 할당되지 않았으므로 null이어야 합니다.");
    }
}
