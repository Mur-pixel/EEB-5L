package com.example.monoproj.game_chip.controller;

import com.example.monoproj.game_chip.controller.request_form.ListGameChipRequestForm;
import com.example.monoproj.game_chip.controller.request_form.RegisterGameChipRequestForm;
import com.example.monoproj.game_chip.controller.request_form.UpdateGameChipRequestForm;
import com.example.monoproj.game_chip.controller.response_form.ListGameChipResponseForm;
import com.example.monoproj.game_chip.controller.response_form.ReadGameChipResponseForm;
import com.example.monoproj.game_chip.controller.response_form.RegisterGameChipResponseForm;
import com.example.monoproj.game_chip.controller.response_form.UpdateGameChipResponseForm;
import com.example.monoproj.game_chip.service.GameChipService;
import com.example.monoproj.game_chip.service.request.RegisterGameChipImageRequest;
import com.example.monoproj.game_chip.service.request.RegisterGameChipRequest;
import com.example.monoproj.game_chip.service.request.UpdateGameChipImageRequest;
import com.example.monoproj.game_chip.service.request.UpdateGameChipRequest;
import com.example.monoproj.game_chip.service.response.ListGameChipResponse;
import com.example.monoproj.game_chip.service.response.ReadGameChipResponse;
import com.example.monoproj.game_chip.service.response.RegisterGameChipResponse;
import com.example.monoproj.game_chip.service.response.UpdateGameChipResponse;
import com.example.monoproj.redis_cache.service.RedisCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/game-chip")
public class GameChipController {

    final private GameChipService gameChipService;
    final private RedisCacheService redisCacheService;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RegisterGameChipResponseForm create(
            @RequestHeader("Authorization") String authorization,
            @ModelAttribute RegisterGameChipRequestForm requestForm) throws IOException {

        String token = extractToken(authorization);
        Long accountId = redisCacheService.getValueByKey(token, Long.class);
        if (accountId == null) {
            throw new RuntimeException("Invalid or expired token");
        }

        log.info("요청 폼 내용 - title: {}, description: {}, price: {}, thumbnailFile: {}, imageFileList size: {}",
                requestForm.getTitle(),
                requestForm.getDescription(),
                requestForm.getPrice(),
                requestForm.getThumbnailFile() != null ? requestForm.getThumbnailFile().getOriginalFilename() : "null",
                requestForm.getImageFileList() != null ? requestForm.getImageFileList().size() : 0);

        if (requestForm.getImageFileList() != null) {
            requestForm.getImageFileList().forEach(file -> {
                log.info("추가 이미지 파일 이름: {}", file.getOriginalFilename());
            });
        }

        RegisterGameChipRequest gameChipRequest = requestForm.toRegisterGameChipRequest(accountId);
        RegisterGameChipImageRequest gameChipImageRequest = requestForm.toRegisterGameChipImageRequest();
        RegisterGameChipResponse response = gameChipService.createGameChip(gameChipRequest, gameChipImageRequest);
        return RegisterGameChipResponseForm.from(response);
    }

    @GetMapping("/list")
    public ListGameChipResponseForm list(@ModelAttribute ListGameChipRequestForm requestForm) {
        ListGameChipResponse response = gameChipService.getAllGameChips(requestForm.toListGameChipRequest());
        return ListGameChipResponseForm.from(response);
    }

    @GetMapping("/read/{id}")
    public ReadGameChipResponseForm read(@PathVariable("id") Long gameChipId) {
        ReadGameChipResponse response = gameChipService.readGameChip(gameChipId);
        return ReadGameChipResponseForm.from(response);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@RequestHeader("Authorization") String authorization,
                       @PathVariable("id") Long gameChipId) {

        String token = extractToken(authorization);
        Long accountId = redisCacheService.getValueByKey(token, Long.class);
        if (accountId == null) {
            throw new RuntimeException("Invalid or expired token");
        }

        log.info("Deleting GameChip id: {}", gameChipId);
        log.info("Account id from token: {}", accountId);

        gameChipService.deleteGameChip(gameChipId, accountId);
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UpdateGameChipResponseForm update(
            @RequestHeader("Authorization") String authorization,
            @PathVariable("id") Long gameChipId,
            @ModelAttribute UpdateGameChipRequestForm requestForm
    ) throws IOException {

        log.info("Update Request: {}", requestForm);

        String token = extractToken(authorization);
        Long accountId = redisCacheService.getValueByKey(token, Long.class);
        if (accountId == null) {
            throw new RuntimeException("Invalid or expired token");
        }

        UpdateGameChipRequest gameChipRequest = requestForm.toUpdateGameChipRequest(accountId);
        UpdateGameChipImageRequest gameChipImageRequest = requestForm.toUpdateGameChipImageRequest();

        UpdateGameChipResponse response = gameChipService.updateGameChip(
                gameChipId,
                gameChipRequest,
                gameChipImageRequest
        );
        return UpdateGameChipResponseForm.from(response);
    }

    private String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        throw new RuntimeException("Invalid Authorization header");
    }
}
