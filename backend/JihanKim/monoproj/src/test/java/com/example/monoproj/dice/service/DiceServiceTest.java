package com.example.monoproj.dice.service;

import com.example.monoproj.dice.controller.request_form.DiceRollResultRequestForm;
import com.example.monoproj.dice.entity.Dice;
import com.example.monoproj.dice.repository.DiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DiceServiceTest {

    @Mock
    private DiceRepository gameRepository;

    @InjectMocks
    private DiceServiceImpl gameService;

    private Dice mockDice;

    @BeforeEach
    void setUp() {
        mockDice = new Dice();
        ReflectionTestUtils.setField(mockDice, "number", 3);
    }

    @Test
    void saveRollResult는_dice_번호_저장_성공여부를_리턴함() {
        when(gameRepository.save(any(Dice.class))).thenReturn(mockDice);
        DiceRollResultRequestForm requestForm = new DiceRollResultRequestForm(3);

        Boolean result = gameService.saveRollResult(requestForm);

        assertEquals(true, result);
    }
}
