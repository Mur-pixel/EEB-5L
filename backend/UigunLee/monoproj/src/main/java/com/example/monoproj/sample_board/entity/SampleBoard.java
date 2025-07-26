package com.example.monoproj.sample_board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class SampleBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sampleBoardId;

    private String title;

    @Column(nullable = true)
    private String nickname;

    private String content;

    private LocalDateTime createDate = LocalDateTime.now();

    public SampleBoard(String title, String nickname, String content) {
        this.title = title;
        this.nickname = nickname;
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
