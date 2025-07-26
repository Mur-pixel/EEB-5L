package com.example.monoproj.sample_board.repository;

import com.example.monoproj.sample_board.entitiy.SampleBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SampleBoardRepository extends JpaRepository<SampleBoard, Long> {

    @Query("SELECT b FROM SampleBoard b JOIN FETCH b.writer ORDER BY b.boardId DESC")
    Page<SampleBoard> findAllWithWriter(Pageable pageable);
}
