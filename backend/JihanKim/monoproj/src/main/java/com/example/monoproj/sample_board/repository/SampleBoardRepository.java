package com.example.monoproj.sample_board.repository;

import com.example.monoproj.sample_board.entity.SampleBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface SampleBoardRepository extends JpaRepository<SampleBoard, Long> {
}
