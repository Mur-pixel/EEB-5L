package com.example.monoproj.game_chip.controller.request_form;

import com.example.monoproj.game_chip.service.request.RegisterGameChipImageRequest;
import com.example.monoproj.game_chip.service.request.RegisterGameChipRequest;
import com.example.monoproj.game_chip.service.request.UpdateGameChipImageRequest;
import com.example.monoproj.game_chip.service.request.UpdateGameChipRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor
public class UpdateGameChipRequestForm {
    final private String title;
    final private String description;
    final private int price;

    final private MultipartFile thumbnailFile;
    final private List<MultipartFile> imageFileList;
    final private List<Integer> removeDetailImageIndexes;

    public UpdateGameChipRequest toUpdateGameChipRequest(Long accountId) {
        return new UpdateGameChipRequest(
                title,
                description,
                price,
                accountId
        );
    }

    public UpdateGameChipImageRequest toUpdateGameChipImageRequest() {
        return new UpdateGameChipImageRequest(thumbnailFile, imageFileList, removeDetailImageIndexes);
    }
}

