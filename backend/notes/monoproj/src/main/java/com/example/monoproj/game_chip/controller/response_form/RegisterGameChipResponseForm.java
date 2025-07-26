package com.example.monoproj.game_chip.controller.response_form;

import com.example.monoproj.game_chip.service.response.RegisterGameChipResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class RegisterGameChipResponseForm {
    private final Long id;
    private final String title;
    private final String description;
    private final int price;

    private final String thumbnailImageBase64;
    private final List<String> detailImageBase64List;

    public static RegisterGameChipResponseForm from(RegisterGameChipResponse response) {
        String thumbnailBase64 = null;
        if (response.getThumbnailImageData() != null) {
            thumbnailBase64 = Base64.getEncoder().encodeToString(response.getThumbnailImageData());
        }

        List<String> detailBase64List = null;
        if (response.getDetailImageDataList() != null) {
            detailBase64List = response.getDetailImageDataList().stream()
                    .map(Base64.getEncoder()::encodeToString)
                    .collect(Collectors.toList());
        }

        return new RegisterGameChipResponseForm(
                response.getId(),
                response.getTitle(),
                response.getDescription(),
                response.getPrice(),
                thumbnailBase64,
                detailBase64List
        );
    }
}
