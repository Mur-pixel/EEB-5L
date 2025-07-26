package com.example.monoproj.game_chip.service.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateGameChipRequest {
    private final String title;
    private final String description;
    private final int price;

    private final Long accountId;
}
