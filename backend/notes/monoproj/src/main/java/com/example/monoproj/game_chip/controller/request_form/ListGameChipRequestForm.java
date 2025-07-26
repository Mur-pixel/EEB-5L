package com.example.monoproj.game_chip.controller.request_form;

import com.example.monoproj.game_chip.service.request.ListGameChipRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ListGameChipRequestForm {
    private final int page;
    private final int perPage;

    public ListGameChipRequest toListGameChipRequest() {
        return new ListGameChipRequest(page, perPage);
    }
}
