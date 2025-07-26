package com.example.demo.sample_board.repository;

import com.example.demo.sample_board.entity.SampleBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleBoardRepository extends JpaRepository<SampleBoard, Long> {
}
