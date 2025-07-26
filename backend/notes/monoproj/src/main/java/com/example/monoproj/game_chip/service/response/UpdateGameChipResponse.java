package com.example.monoproj.game_chip.service.response;

import com.example.monoproj.game_chip.entity.GameChip;
import com.example.monoproj.game_chip.entity.GameChipImage;
import com.example.monoproj.game_chip.entity.GameChipImageType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class UpdateGameChipResponse {
    final private Long id;
    final private String title;
    final private String description;
    final private int price;

    private final byte[] thumbnailImageData;
    private final List<byte[]> detailImageDataList;

    public static UpdateGameChipResponse from(GameChip gameChip, List<GameChipImage> imageList) {
        byte[] thumbnailImageData = imageList.stream()
                .filter(img -> img.getType() == GameChipImageType.THUMBNAIL)
                .map(GameChipImage::getImageData)
                .findFirst()
                .orElse(null);

        List<byte[]> detailImageDataList = imageList.stream()
                .filter(img -> img.getType() == GameChipImageType.DETAIL)
                .map(GameChipImage::getImageData)
                .collect(Collectors.toList());

        return new UpdateGameChipResponse(
                gameChip.getId(),
                gameChip.getTitle(),
                gameChip.getDescription(),
                gameChip.getPrice(),
                thumbnailImageData,
                detailImageDataList
        );
    }
}
