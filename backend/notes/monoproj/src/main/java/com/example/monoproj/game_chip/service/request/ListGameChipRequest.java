package com.example.monoproj.game_chip.service.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ListGameChipRequest {
    private final int page;
    private final int perPage;
}
