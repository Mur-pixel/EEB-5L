package com.example.monoproj.sample_board.service.request;


import com.example.monoproj.sample_board.entity.SampleBoard;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
// 게시글 생성을 위한 요청 정보를 담는 DTO 클래스
public class CreateSampleBoardRequest {

    // 게시글 제목 - 외부에서 전달받는 값
    final private String title;

    // 게시글 내용 - 외부에서 전달받는 값
    final private String content;
    final private String nickname;
    // DTO를 엔티티(SampleBoard) 객체로 변환하는 메서드
    // Service 계층에서 save() 하기 전에 호출됨
    public SampleBoard toBoard() {
        // SampleBoard는 실제 DB 테이블과 매핑되는 JPA 엔티티 클래스임
        return new SampleBoard(title, content,nickname);
    }
}
