package com.example.monoproj.game_chip.repository;

import com.example.monoproj.game_chip.entity.GameChip;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;

public interface GameChipRepository extends JpaRepository<GameChip, Long> {
}
