package com.example.monoproj.board.controller;

import com.example.monoproj.board.controller.request_form.CreateBoardRequestForm;
import com.example.monoproj.board.controller.request_form.ListBoardRequestForm;
import com.example.monoproj.board.controller.request_form.UpdateBoardRequestForm;
import com.example.monoproj.board.controller.response_form.CreateBoardResponseForm;
import com.example.monoproj.board.controller.response_form.ListBoardResponseForm;
import com.example.monoproj.board.controller.response_form.ReadBoardResponseForm;
import com.example.monoproj.board.controller.response_form.UpdateBoardResponseForm;
import com.example.monoproj.board.service.BoardService;
import com.example.monoproj.board.service.response.CreateBoardResponse;
import com.example.monoproj.board.service.response.ListBoardResponse;
import com.example.monoproj.board.service.response.ReadBoardResponse;
import com.example.monoproj.board.service.response.UpdateBoardResponse;
import com.example.monoproj.redis_cache.service.RedisCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * BoardController는 게시판 기능에 대한 REST API 엔드포인트를 제공합니다.
 * 주요 책임:
 *  1. HTTP 요청을 받아 적절한 서비스 호출로 변환
 *  2. 요청 검증 및 인증 토큰 추출
 *  3. 서비스 응답을 API 응답 포맷으로 변환 후 반환
 *  4. 전반 로깅을 통해 운영 중 이슈 추적 및 성능 분석 지원
 */
// @Slf4j
// 도메인 관점: 서비스 계층의 핵심 비즈니스 흐름(게시글 등록·조회·수정 등)에 대한 로그를 기록합니다.
//            운영 환경에서 사용자 행동과 시스템 상태를 추적하고, 장애 원인 분석 및 감사(audit) 용도로 활용됩니다.
@Slf4j

// @RestController
// 도메인 관점: 이 클래스가 '게시판(Board)' 도메인을 RESTful HTTP API로 외부에 노출함을 선언합니다.
//            모든 메서드가 JSON 형식으로 직렬화되어 클라이언트와 도메인 데이터를 주고받을 수 있게 합니다.
@RestController

// @RequiredArgsConstructor
// 도메인 관점: 도메인 서비스(BoardService)와 캐시 서비스(RedisCacheService)를 불변(final) 의존성으로 주입합니다.
//            생성자 주입을 자동 생성하여, 서비스 간 결합도를 낮추고 테스트 시 목(Mock) 객체 주입을 쉽게 만듭니다.
@RequiredArgsConstructor

// @RequestMapping("/board")
// 도메인 관점: '게시판(Board)' 도메인의 기본 URI 경로를 '/board'로 설정합니다.
//            이 경로 하위에 'list', 'register', 'read/{id}', 'update/{id}' 등 도메인 행위별 엔드포인트를 정의합니다.
@RequestMapping("/board")
public class BoardController {

    /**
     * BoardService: 비즈니스 로직 계층으로, 게시글 CRUD 처리를 위임합니다.
     * RedisCacheService: 인증 토큰 기반으로 Redis에서 accountId 조회에 사용됩니다.
     */
    private final BoardService boardService;
    private final RedisCacheService redisCacheService;

    /**
     * 게시글 목록 조회 엔드포인트
     * - HTTP GET /board/list
     * - 클라이언트는 ?page={page}&perPage={perPage} 쿼리 파라미터로 페이징 요청을 전달
     * - Spring은 @ModelAttribute로 바인딩하여 ListBoardRequestForm 생성
     * - 서비스에 DTO로 변환 후 전달, 페이징된 결과를 받아 ListBoardResponseForm으로 변환하여 반환
     * - 반환 포맷의 이점: API 스펙 변경 시 컨트롤러 레벨에서만 수정, 서비스와 분리되어 유지보수성 향상
     */
    @GetMapping("/list")
    public ListBoardResponseForm boardList(@ModelAttribute ListBoardRequestForm requestForm) {
        // 요청 파라미터 로깅 (운영 환경에서 트래픽 분석 및 디버깅 용도)
        log.info("[boardList] requestForm = {}", requestForm);

        // 서비스 호출: ListBoardRequestForm -> ListBoardRequest로 변환
        ListBoardResponse response = boardService.list(requestForm.toListBoardRequest());

        // ListBoardResponseForm.from: 서비스 계층 응답을 API 응답 형태로 매핑
        // List.of(response)로 리스트 감싸서, 총 아이템 수와 전체 페이지 수 포함
        return ListBoardResponseForm.from(
                List.of(response),
                (int) response.getTotalItems(),
                response.getTotalPages()
        );
    }

    /**
     * 게시글 등록 엔드포인트
     * - HTTP POST /board/register
     * - Authorization 헤더를 통해 Bearer 토큰을 전달받아 사용자 인증 수행
     * - JSON Body를 CreateBoardRequestForm으로 매핑
     * - Redis에서 accountId 조회 후, 서비스에 요청 DTO 생성하여 전달
     * - 응답 DTO를 CreateBoardResponseForm으로 변환하여 반환
     */
    @PostMapping("/register")
    public CreateBoardResponseForm registerBoard(
            // HTTP 요청 헤더에서 "Authorization" 값을 꺼내어 authorizationHeader 변수에 저장합니다.
// 도메인 관점: 이 토큰을 통해 “누가” 요청을 보냈는지 식별하여, 이후 Redis나 JWT 검증으로 accountId를 얻고
//             서비스 계층에 전달된 모든 동작이 인증된 사용자만 수행하도록 보장합니다.
            @RequestHeader("Authorization") String authorizationHeader,

// HTTP 요청 바디의 JSON 페이로드를 CreateBoardRequestForm 타입으로 직렬화(바인딩)합니다.
// 도메인 관점: 클라이언트가 전달한 게시글 생성 정보(제목, 내용 등)를 이 DTO에 모아두어,
//             이후 toCreateBoardRequest(accountId) 호출을 통해 도메인 서비스 레이어의
//             CreateBoardRequest 모델로 변환하여 비즈니스 로직에 넘깁니다.
            @RequestBody CreateBoardRequestForm createBoardRequestForm

    ) {
        log.info("[registerBoard] requestForm = {}", createBoardRequestForm);
        log.info("[registerBoard] Authorization header = {}", authorizationHeader);

        // "Bearer {token}" 형식에서 실제 토큰만 추출
        String token = authorizationHeader.replace("Bearer ", "").trim();

        // RedisCacheService로 토큰을 키로 사용해 사용자 계정 ID 조회
        Long accountId = redisCacheService.getValueByKey(token);
        log.info("[registerBoard] resolved accountId = {}", accountId);

        // 서비스 호출: 폼 -> 도메인 요청 객체로 변환하여 전달
        CreateBoardResponse svcResponse = boardService.register(
                createBoardRequestForm.toCreateBoardRequest(accountId)
        );

        // 서비스 응답을 API 응답 포맷으로 변환하여 반환
        return CreateBoardResponseForm.from(svcResponse);
    }

    /**
     * 게시글 상세 조회 엔드포인트
     * - HTTP GET /board/read/{boardId}
     * - @PathVariable로 URL 경로에서 boardId 추출
     * - 서비스 호출 후 ReadBoardResponse를 ReadBoardResponseForm으로 변환
     */
    @GetMapping("/read/{boardId}")
    public ReadBoardResponseForm readBoard(@PathVariable("boardId") Long boardId) {
        log.info("[readBoard] boardId = {}", boardId);

        ReadBoardResponse svcResponse = boardService.read(boardId);

        // null 처리가 가능하므로, 예외 대신 빈 폼 반환 등 추가 로직 고려 가능
        return ReadBoardResponseForm.from(svcResponse);
    }

    /**
     * 게시글 수정 엔드포인트
     * - HTTP PUT /board/update/{boardId}
     * - PathVariable로 boardId, Body로 수정 내용, Header로 인증 토큰을 전달받음
     * - Redis에서 accountId 조회 후 서비스 호출
     * - 작성자 권한 체크는 서비스 계층에서 수행
     */
    @PutMapping("/update/{boardId}")
    public UpdateBoardResponseForm updateBoard(
            @PathVariable("boardId") Long boardId,
            @RequestBody UpdateBoardRequestForm updateBoardRequestForm,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        log.info("[updateBoard] requestForm = {}, boardId = {}", updateBoardRequestForm, boardId);

        // 토큰 추출 및 Redis 조회
        String token = authorizationHeader.replace("Bearer ", "").trim();
        Long accountId = redisCacheService.getValueByKey(token);
        log.info("[updateBoard] resolved accountId = {}", accountId);

        // 서비스 호출: boardId, accountId, 수정 요청 DTO 전달
        UpdateBoardResponse svcResponse = boardService.update(
                boardId,
                accountId,
                updateBoardRequestForm.toUpdateBoardRequest()
        );

        // 서비스 응답을 API 응답 포맷으로 변환하여 반환
        return UpdateBoardResponseForm.from(svcResponse);
    }
}
