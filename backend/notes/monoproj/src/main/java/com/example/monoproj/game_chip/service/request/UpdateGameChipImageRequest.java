package com.example.monoproj.game_chip.service.request;

import com.example.monoproj.game_chip.entity.GameChip;
import com.example.monoproj.game_chip.entity.GameChipImage;
import com.example.monoproj.game_chip.entity.GameChipImageType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class UpdateGameChipImageRequest {
    final private MultipartFile thumbnailFile;
    final private List<MultipartFile> imageFileList;
    final private List<Integer> removeDetailImageIndexes;

    public GameChipImage toGameChipThumbnail(GameChip gameChip) throws IOException {
        return new GameChipImage(
                thumbnailFile.getBytes(),
                thumbnailFile.getOriginalFilename(),
                GameChipImageType.THUMBNAIL,
                gameChip
        );
    }

    public List<GameChipImage> toGameChipImageList(GameChip gameChip) throws IOException {
        List<GameChipImage> images = new ArrayList<>();

        for (MultipartFile file : imageFileList) {
            images.add(new GameChipImage(
                    file.getBytes(),
                    file.getOriginalFilename(),
                    GameChipImageType.DETAIL,
                    gameChip
            ));
        }

        return images;
    }
}
