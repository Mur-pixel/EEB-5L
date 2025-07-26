package com.example.monoproj.sample_board.repository;

import com.example.monoproj.sample_board.entity.SampleBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SampleBoardRepository extends JpaRepository<SampleBoard, Long> {

    @Query("SELECT b FROM SampleBoard b JOIN FETCH b.sample_Board_writer ORDER BY b.sample_board_id DESC")
    Page<SampleBoard> findAllWithWriter(Pageable pageable);

    @Query("SELECT b FROM SampleBoard b JOIN FETCH b.sample_Board_writer WHERE b.sample_board_id = :sample_board_id")
    Optional<SampleBoard> findByIdWithWriter(Long boardId);
}
