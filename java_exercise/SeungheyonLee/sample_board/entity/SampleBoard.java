package com.example.monoproj.sample_board.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 도메인 엔티티: 인증 없이 누구나 글을 쓸 수 있는 샘플 게시판
 * - AccountProfile 등 작성자 정보는 제거해, 순수하게 제목·내용만 저장합니다.
 */
@Entity
@Table(name = "sample_board")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class SampleBoard {

    /** PK: 자동 생성되는 게시글 ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//AUTO_INCREMENT
    private Long boardId;

    /** 게시글 제목 */
    private String title;

    /** 게시글 내용 */
    private String content;

    private String nickname;

    /** 생성 시각 (자동 적용) */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @CreationTimestamp
    private LocalDateTime createDate;

    /** 수정 시각 (자동 적용) */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @UpdateTimestamp
    private LocalDateTime updateDate;

    /**
     * 생성자: 제목과 내용만 받아 새로운 엔티티를 만듭니다.
     * 인증을 배제한 샘플 게시판이므로 작성자 필드는 없습니다.
     */
    public SampleBoard(String title, String content,String nickname) {
        this.title = title;
        this.content = content;
        this.nickname=nickname;
    }

}
