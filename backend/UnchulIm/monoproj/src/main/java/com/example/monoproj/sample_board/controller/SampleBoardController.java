package com.example.monoproj.sample_board.controller;

import com.example.monoproj.sample_board.controller.request_form.CreateSampleBoardRequestForm;
import com.example.monoproj.sample_board.controller.request_form.ListSampleBoardRequestForm;
import com.example.monoproj.sample_board.controller.request_form.UpdateSampleBoardRequestForm;
import com.example.monoproj.sample_board.controller.response_form.CreateSampleBoardResponseForm;
import com.example.monoproj.sample_board.controller.response_form.ListSampleBoardResponseForm;
import com.example.monoproj.sample_board.controller.response_form.ReadSampleBoardResponseForm;
import com.example.monoproj.sample_board.controller.response_form.UpdateSampleBoardResponseForm;

import com.example.monoproj.sample_board.service.SampleBoardService;
import com.example.monoproj.sample_board.service.response.CreateSampleBoardResponse;
import com.example.monoproj.sample_board.service.response.ListSampleBoardResponse;
import com.example.monoproj.sample_board.service.response.ReadSampleBoardResponse;
import com.example.monoproj.sample_board.service.response.UpdateSampleBoardResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 인증 없이 누구나 게시글을 등록할 수 있는 샘플 게시판 API 컨트롤러
 */
@Slf4j                                       // 로깅을 위해 Logger 자동 생성
@RestController                             // JSON 응답을 기본으로 하는 REST 컨트롤러
@RequiredArgsConstructor                    // final 필드 생성자 자동 주입
@CrossOrigin(origins = "*")                  // 모든 출처에서의 AJAX/CORS 요청 허용
@RequestMapping("/sample-board")             // 기본 URL 프리픽스 설정
public class SampleBoardController {

    final private SampleBoardService sampleBoardService;

//    @GetMapping("/list")
//    public ListSampleBoardResponseForm sampleBoardList(@ModelAttribute ListSampleBoardRequestForm requestForm) {
//        log.info("boardList() -> {}", requestForm);
//
//        ListSampleBoardResponse response = sampleBoardService.list(requestForm.toListSampleBoardRequest());
//
//        return ListSampleBoardResponseForm.from(
//                List.of(response),
//                (int) response.getTotalItems(),
//                response.getTotalPages()
//        );
//    }

    @PostMapping("/register")
    public CreateSampleBoardResponseForm registerSampleBoard(
            @RequestBody CreateSampleBoardRequestForm createSampleBoardRequestForm) {
        log.info("registerBoard() -> {}", createSampleBoardRequestForm);
        CreateSampleBoardResponse response = sampleBoardService.register(
                createSampleBoardRequestForm.toCreateSampleBoardRequest()
        );
        System.out.println(response);
        return CreateSampleBoardResponseForm.from(response);
    }
//
//    @GetMapping("/read/{boardId}")
//    public ReadSampleBoardResponseForm readSampleBoard(@PathVariable("boardId") Long boardId) {
//        log.info("BoardRead(): {}", boardId);
//        ReadSampleBoardResponse response = sampleBoardService.read(boardId);
//        return ReadSampleBoardResponseForm.from(response);
//    }
//
//    @PutMapping("/update/{boardId}")
//    public UpdateSampleBoardResponseForm updateSampleBoard(
//            @PathVariable("boardId") Long boardId,
//            @RequestBody UpdateSampleBoardRequestForm updateSampleBoardRequestForm) {
//
//        log.info("modifyBoard(): {}, boardId: {}", updateSampleBoardRequestForm, boardId);
//
//        UpdateSampleBoardResponse response = sampleBoardService.update(
//                boardId,
//                updateSampleBoardRequestForm.toUpdateSampleBoardRequest()
//        );
//
//        return UpdateSampleBoardResponseForm.from(response);
//    }

}
/**
 * 전체 흐름 주석
 *
 * 1. HTTP 요청 (Client → Controller)
 *    - Postman 등에서 다음과 같은 JSON POST 요청을 보냄
 *      POST /sample-board/register
 *      {
 *        "title": "...",
 *        "content": "..."
 *      }
 *
 * 2. Controller 계층 (SampleBoardController.registerBoard)
 *    - @RequestBody로 JSON을 CreateSampleBoardRequestForm에 바인딩
 *    - form.toCreateSampleBoardRequest() 호출하여
 *      Controller 폼 → Service 요청 DTO(CreateSampleBoardRequest)로 변환
 *    - boardService.register(requestDTO) 호출
 *
 * 3. Service 계층 (SampleBoardServiceImpl.register)
 *    - Service 요청 DTO에서 title, content를 꺼내
 *      new SampleBoard(entity) 생성
 *    - repository.save(entity) 호출하여 DB에 INSERT
 *    - 저장된 엔티티를 기반으로 CreateSampleBoardResponse 생성 후 반환
 *
 * 4. Repository 계층 (SampleBoardRepository.save)
 *    - JPA가 INSERT SQL 생성
 *    - sample_board 테이블에 새 행 저장, 자동 생성된 ID, 타임스탬프 채워진 엔티티 반환
 *
 * 5. Service 응답 변환
 *    - 저장된 엔티티 정보로 CreateSampleBoardResponse DTO 생성
 *
 * 6. Controller 응답 변환
 *    - Service 응답 DTO를 CreateSampleBoardResponseForm으로 매핑
 *    - Spring이 이 Form을 JSON으로 직렬화하여 클라이언트에 반환
 *
 * 7. HTTP 응답 (Controller → Client)
 *    - 클라이언트는 다음과 같은 JSON 응답을 받음
 *      {
 *        "id": 123,
 *        "title": "...",
 *        "content": "..."
 *      }
 *
 * → Controller ↔ Service ↔ Repository ↔ DB 로 이어지는 전통적인 레이어드 아키텍처 흐름
 */
