package com.example.monoproj.game_chip.controller.request_form;

import com.example.monoproj.game_chip.service.request.RegisterGameChipImageRequest;
import com.example.monoproj.game_chip.service.request.RegisterGameChipRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class RegisterGameChipRequestForm {
    final private String title;
    final private String description;
    final private int price;

    final private MultipartFile thumbnailFile;
    final private List<MultipartFile> imageFileList;

    public RegisterGameChipRequest toRegisterGameChipRequest(Long accountId) {
        return new RegisterGameChipRequest(title, description, price, accountId);
    }

    public RegisterGameChipImageRequest toRegisterGameChipImageRequest() {
        return new RegisterGameChipImageRequest(thumbnailFile, imageFileList);
    }
}

