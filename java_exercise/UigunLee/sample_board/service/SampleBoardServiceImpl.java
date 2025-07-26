package com.example.monoproj.sample_board.service;

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

@Slf4j
@Service
@RequiredArgsConstructor
public class SampleBoardServiceImpl implements SampleBoardService {

    private final SampleBoardRepository sampleBoardRepository;

    @Override
    public ListSampleBoardResponse list(ListSampleBoardRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getPage() - 1, request.getPerPage());
        Page<SampleBoard> sampleBoardPage = sampleBoardRepository.findAll(pageRequest); // writer 없으니 단순 조회

        return new ListSampleBoardResponse(
                sampleBoardPage.getContent(),
                sampleBoardPage.getTotalElements(),
                sampleBoardPage.getTotalPages()
        );
    }

    @Override
    public CreateSampleBoardResponse register(CreateSampleBoardRequest createSampleBoardRequest) {
        log.info("nickname: {}", createSampleBoardRequest.getNickname());

        SampleBoard sampleBoard = sampleBoardRepository.save(
                createSampleBoardRequest.toSampleBoard()
        );
        return CreateSampleBoardResponse.from(sampleBoard);
    }

    @Override
    public ReadSampleBoardResponse read(Long sampleBoardId) {
        SampleBoard sampleBoard = sampleBoardRepository.findById(sampleBoardId)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        return ReadSampleBoardResponse.from(sampleBoard);
    }

    @Override
    public UpdateSampleBoardResponse update(Long sampleBoardId, String nickname, UpdateSampleBoardRequest updateRequest) {
        SampleBoard sampleBoard = sampleBoardRepository.findById(sampleBoardId)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        // 작성자 검증: 닉네임 비교
        if (!sampleBoard.getNickname().equals(nickname)) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        if (updateRequest.getTitle() != null) {
            sampleBoard.setTitle(updateRequest.getTitle());
        }
        if (updateRequest.getContent() != null) {
            sampleBoard.setContent(updateRequest.getContent());
        }

        sampleBoardRepository.save(sampleBoard);
        return UpdateSampleBoardResponse.from(sampleBoard);
    }

}
