package com.example.monoproj.sampleBoard.repository;

import com.example.monoproj.board.entity.Board;
import com.example.monoproj.sampleBoard.entity.SampleBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SampleBoardRepository extends JpaRepository<SampleBoard, Long> {



    @Query("SELECT s FROM SampleBoard s JOIN FETCH s.writer WHERE s.sampleBoardId = :sampleBoardId")
    Optional<SampleBoard> findByIdWithWriter(Long sampleBoardId);

    @Query("SELECT s FROM SampleBoard s JOIN FETCH s.writer")
    List<SampleBoard> findAllWithWriter();


}
