package com.example.monoproj.game_chip.repository;

import com.example.monoproj.game_chip.entity.GameChip;
import com.example.monoproj.game_chip.entity.GameChipImage;
import com.example.monoproj.game_chip.entity.GameChipImageType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameChipImageRepository extends JpaRepository<GameChipImage, Long> {
    List<GameChipImage> findAllByGameChipInAndType(List<GameChip> gameChips, GameChipImageType type);
    List<GameChipImage> findAllByGameChipId(Long gameChipId);

    void deleteAllByGameChip(GameChip gameChip);

    void deleteByGameChipAndType(GameChip existingGameChip, GameChipImageType gameChipImageType);

    List<GameChipImage> findByGameChipAndTypeOrderByIdAsc(GameChip existingGameChip, GameChipImageType gameChipImageType);
}
