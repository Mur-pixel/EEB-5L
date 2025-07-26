package com.example.monoproj.game_chip.controller.response_form;

import com.example.monoproj.game_chip.service.response.UpdateGameChipResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class UpdateGameChipResponseForm {

    private final Long id;
    private final String title;
    private final String description;
    private final int price;

    private final String thumbnailImageBase64;
    private final List<String> detailImageBase64List;

    public static UpdateGameChipResponseForm from(UpdateGameChipResponse response) {
        String thumbnailImageBase64 = null;
        if (response.getThumbnailImageData() != null) {
            thumbnailImageBase64 = Base64.getEncoder().encodeToString(response.getThumbnailImageData());
        }

        List<String> detailImageBase64List = response.getDetailImageDataList().stream()
                .map(imgBytes -> Base64.getEncoder().encodeToString(imgBytes))
                .collect(Collectors.toList());

        return new UpdateGameChipResponseForm(
                response.getId(),
                response.getTitle(),
                response.getDescription(),
                response.getPrice(),
                thumbnailImageBase64,
                detailImageBase64List
        );
    }
}
