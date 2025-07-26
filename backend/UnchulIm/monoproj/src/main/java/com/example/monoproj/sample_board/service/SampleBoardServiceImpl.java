package com.example.monoproj.sample_board.service;

import com.example.monoproj.account.entity.Account;
import com.example.monoproj.account.repository.AccountRepository;
import com.example.monoproj.account_profile.entity.AccountProfile;
import com.example.monoproj.account_profile.repository.AccountProfileRepository;
import com.example.monoproj.sample_board.entity.SampleBoard;
import com.example.monoproj.sample_board.repository.SampleBoardRepository;
import com.example.monoproj.sample_board.service.request.CreateSampleBoardRequest;
import com.example.monoproj.sample_board.service.request.ListSampleBoardRequest;
import com.example.monoproj.sample_board.service.request.UpdateSampleBoardRequest;
import com.example.monoproj.sample_board.service.response.CreateSampleBoardResponse;
import com.example.monoproj.sample_board.service.response.ListSampleBoardResponse;
import com.example.monoproj.sample_board.service.response.ReadSampleBoardResponse;
import com.example.monoproj.sample_board.service.response.UpdateSampleBoardResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SampleBoardServiceImpl implements SampleBoardService {

    final private SampleBoardRepository sampleBoardRepository;

    @Override
    public ListSampleBoardResponse list(ListSampleBoardRequest request) {
        int page = request.getPage();
        int perPage = request.getPerPage();

        PageRequest pageRequest = PageRequest.of(page, perPage);
        Page<SampleBoard> pageResult = sampleBoardRepository.findAll(pageRequest);

        List<SampleBoard> content = pageResult.getContent();
        long totalItems = pageResult.getTotalElements();
        int totalPages = pageResult.getTotalPages();

        return new ListSampleBoardResponse(content, totalItems, totalPages);
    }

    @Override
    public CreateSampleBoardResponse register(CreateSampleBoardRequest request) {
        log.info("Registering post: {}", request);
        SampleBoard sampleBoard = sampleBoardRepository.save(request.toSampleBoard());
        return CreateSampleBoardResponse.from(sampleBoard);
    }

    @Override
    public UpdateSampleBoardResponse update(Long boardId, UpdateSampleBoardRequest request) {
        SampleBoard sampleBoard = sampleBoardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("해당 게시글을 찾을 수 없습니다: id = " + boardId));

        sampleBoard.setTitle(request.getTitle());
        sampleBoard.setContent(request.getContent());

        SampleBoard updated = sampleBoardRepository.save(sampleBoard);
        return UpdateSampleBoardResponse.from(updated);
    }

    @Override
    public ReadSampleBoardResponse read(Long boardId) {
        SampleBoard sampleBoard = sampleBoardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다: id = " + boardId));

        return ReadSampleBoardResponse.from(sampleBoard);
    }

}