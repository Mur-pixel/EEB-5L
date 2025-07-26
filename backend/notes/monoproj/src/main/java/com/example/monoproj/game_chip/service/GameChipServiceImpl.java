package com.example.monoproj.game_chip.service;

import com.example.monoproj.account.entity.Account;
import com.example.monoproj.account.repository.AccountRepository;
import com.example.monoproj.game_chip.controller.response_form.ListGameChipResponseForm;
import com.example.monoproj.game_chip.entity.GameChip;
import com.example.monoproj.game_chip.entity.GameChipImage;
import com.example.monoproj.game_chip.entity.GameChipImageType;
import com.example.monoproj.game_chip.repository.GameChipImageRepository;
import com.example.monoproj.game_chip.repository.GameChipRepository;
import com.example.monoproj.game_chip.service.request.*;
import com.example.monoproj.game_chip.service.response.ListGameChipResponse;
import com.example.monoproj.game_chip.service.response.ReadGameChipResponse;
import com.example.monoproj.game_chip.service.response.RegisterGameChipResponse;
import com.example.monoproj.game_chip.service.response.UpdateGameChipResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameChipServiceImpl implements GameChipService {

    private final GameChipImageRepository gameChipImageRepository;
    private final GameChipRepository gameChipRepository;
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public RegisterGameChipResponse createGameChip(RegisterGameChipRequest gameChipRequest, RegisterGameChipImageRequest gameChipImageRequest) throws IOException {
        Long requestedAccountId = gameChipRequest.getAccountId();
        Account account = accountRepository.findById(requestedAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Account 존재하지 않음"));

        GameChip requestedGameChip = gameChipRequest.toGameChip(account);
        GameChip savedGameChip = gameChipRepository.save(requestedGameChip);

        GameChipImage requestedGameChipThumbnail = gameChipImageRequest.toGameChipThumbnail(savedGameChip);
        GameChipImage savedGameChipThumbnailImage = gameChipImageRepository.save(requestedGameChipThumbnail);

        List<GameChipImage> detailImageList = gameChipImageRequest.toGameChipImageList(savedGameChip);
        List<GameChipImage> savedGameChipDetailImageList = gameChipImageRepository.saveAll(detailImageList);

        List<GameChipImage> allImages = new ArrayList<>();
        allImages.add(savedGameChipThumbnailImage);
        allImages.addAll(savedGameChipDetailImageList);

        return RegisterGameChipResponse.from(savedGameChip, allImages);
    }

    @Override
    public ListGameChipResponse getAllGameChips(ListGameChipRequest request) {
        int page = request.getPage() - 1;
        int perPage = request.getPerPage();

        PageRequest pageRequest = PageRequest.of(page, perPage);
        Page<GameChip> pageResult = gameChipRepository.findAll(pageRequest);

        List<GameChip> gameChips = pageResult.getContent();

        List<GameChipImage> thumbnailImages = gameChipImageRepository.findAllByGameChipInAndType(gameChips, GameChipImageType.THUMBNAIL);
        Map<Long, byte[]> thumbnailMap = thumbnailImages.stream()
                .collect(Collectors.toMap(
                        img -> img.getGameChip().getId(),
                        GameChipImage::getImageData
                ));

        return ListGameChipResponse.from(
                pageResult.getContent(),
                pageResult.getNumber() + 1,   // 1-based current page
                pageResult.getTotalPages(),
                pageResult.getTotalElements(),
                thumbnailMap
        );
    }

    @Override
    public ReadGameChipResponse readGameChip(Long gameChipId) {
        GameChip gameChip = gameChipRepository.findById(gameChipId)
                .orElseThrow(() -> new RuntimeException("GameChip 존재하지 않음 id: " + gameChipId));

        List<GameChipImage> images = gameChipImageRepository.findAllByGameChipId(gameChipId);

        return ReadGameChipResponse.from(gameChip, images);
    }

    @Override
    @Transactional
    public void deleteGameChip(Long gameChipId, Long accountId) {
        GameChip chip = gameChipRepository.findById(gameChipId)
                .orElseThrow(() -> new RuntimeException("GameChip not found"));

        if (!chip.getAccount().getId().equals(accountId)) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }

        gameChipImageRepository.deleteAllByGameChip(chip);
        gameChipRepository.delete(chip);
    }

    @Override
    @Transactional
    public UpdateGameChipResponse updateGameChip(Long gameChipId, UpdateGameChipRequest gameChipRequest, UpdateGameChipImageRequest gameChipImageRequest) throws IOException {
        GameChip existingGameChip = gameChipRepository.findById(gameChipId)
                .orElseThrow(() -> new RuntimeException("GameChip not found with id: " + gameChipId));

        if (!existingGameChip.getAccount().getId().equals(gameChipRequest.getAccountId())) {
            throw new RuntimeException("권한이 없습니다.");
        }

        existingGameChip.setTitle(gameChipRequest.getTitle());
        existingGameChip.setDescription(gameChipRequest.getDescription());
        existingGameChip.setPrice(gameChipRequest.getPrice());

        MultipartFile newThumbnailFile = gameChipImageRequest.getThumbnailFile();
        GameChipImage savedGameChipThumbnailImage = null;
        if (newThumbnailFile != null && !newThumbnailFile.isEmpty()) {
            gameChipImageRepository.deleteByGameChipAndType(existingGameChip, GameChipImageType.THUMBNAIL);
            GameChipImage newThumbnail = gameChipImageRequest.toGameChipThumbnail(existingGameChip);
            savedGameChipThumbnailImage = gameChipImageRepository.save(newThumbnail);
        }

        List<Integer> removeIndexes = gameChipImageRequest.getRemoveDetailImageIndexes();
        if (removeIndexes != null && !removeIndexes.isEmpty()) {
            List<GameChipImage> detailImages = gameChipImageRepository.findByGameChipAndTypeOrderByIdAsc(existingGameChip, GameChipImageType.DETAIL);

            removeIndexes.sort(Comparator.reverseOrder());
            for (Integer idx : removeIndexes) {
                if (idx >= 0 && idx < detailImages.size()) {
                    gameChipImageRepository.delete(detailImages.get(idx));
                }
            }
        }

        List<GameChipImage> savedGameChipDetailImageList = Collections.emptyList();
        List<GameChipImage> newDetailImages = gameChipImageRequest.toGameChipImageList(existingGameChip);
        if (!newDetailImages.isEmpty()) {
            savedGameChipDetailImageList = gameChipImageRepository.saveAll(newDetailImages);
        }

        gameChipRepository.save(existingGameChip);

        List<GameChipImage> allImages = new ArrayList<>();
        if (savedGameChipThumbnailImage != null) {
            allImages.add(savedGameChipThumbnailImage);
        }
        allImages.addAll(savedGameChipDetailImageList);

        return UpdateGameChipResponse.from(existingGameChip, allImages);
    }
}
