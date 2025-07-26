package com.example.monoproj.dice.controller;

import com.example.monoproj.dice.controller.request_form.DiceRollResultRequestForm;
import com.example.monoproj.dice.service.DiceService;
import com.example.monoproj.dice.service.DiceServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DiceService diceService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSaveRollResult() throws Exception {
        DiceRollResultRequestForm requestForm = new DiceRollResultRequestForm(3);

        when(diceService.saveRollResult(requestForm)).thenReturn(true);

        mockMvc.perform(post("/dice/save-roll-result")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestForm)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
