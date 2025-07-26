package com.example.monoproj.sample_board.repository;

import com.example.monoproj.sample_board.entity.SampleBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SampleBoardRepository extends JpaRepository<SampleBoard, Long> {
}