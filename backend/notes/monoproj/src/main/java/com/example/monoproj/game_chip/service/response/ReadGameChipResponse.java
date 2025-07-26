package com.example.monoproj.game_chip.service.response;

import com.example.monoproj.game_chip.entity.GameChip;
import com.example.monoproj.game_chip.entity.GameChipImage;
import com.example.monoproj.game_chip.entity.GameChipImageType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class ReadGameChipResponse {

    private final Long id;
    private final String title;
    private final String description;
    private final int price;

    private final byte[] thumbnailImageData;
    private final List<byte[]> detailImageDataList;

    public static ReadGameChipResponse from(GameChip gameChip, List<GameChipImage> images) {
        byte[] thumbnail = null;
        List<byte[]> detailImages = new ArrayList<>();

        for (GameChipImage image : images) {
            if (image.getType() == GameChipImageType.THUMBNAIL) {
                thumbnail = image.getImageData();
                continue;
            }
            if (image.getType() == GameChipImageType.DETAIL) {
                detailImages.add(image.getImageData());
                continue;
            }
            // 다른 타입이 있을 경우 여기서 처리 가능
        }

        return new ReadGameChipResponse(
                gameChip.getId(),
                gameChip.getTitle(),
                gameChip.getDescription(),
                gameChip.getPrice(),
                thumbnail,
                detailImages
        );
    }

}
