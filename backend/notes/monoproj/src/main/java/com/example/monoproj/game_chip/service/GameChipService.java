package com.example.monoproj.game_chip.service;

import com.example.monoproj.game_chip.service.request.*;
import com.example.monoproj.game_chip.service.response.ListGameChipResponse;
import com.example.monoproj.game_chip.service.response.ReadGameChipResponse;
import com.example.monoproj.game_chip.service.response.RegisterGameChipResponse;
import com.example.monoproj.game_chip.service.response.UpdateGameChipResponse;

import java.io.IOException;

public interface GameChipService {
    RegisterGameChipResponse createGameChip(RegisterGameChipRequest gameChipRequest, RegisterGameChipImageRequest gameChipImageRequest) throws IOException;
    ListGameChipResponse getAllGameChips(ListGameChipRequest request);
    ReadGameChipResponse readGameChip(Long gameChipId);
    void deleteGameChip(Long gameChipId, Long accountId);
    UpdateGameChipResponse updateGameChip(Long gameChipId, UpdateGameChipRequest gameChipRequest, UpdateGameChipImageRequest gameChipImageRequest) throws IOException;
}
