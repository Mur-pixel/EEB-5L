package com.example.monoproj.sample_board.repository;

import com.example.monoproj.sample_board.entity.SampleBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

public interface SampleBoardRepository extends JpaRepository<SampleBoard, Long> {

    Page<SampleBoard> findAllByOrderBySampleBoardIdDesc(Pageable pageable);
}
