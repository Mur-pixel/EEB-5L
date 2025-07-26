package com.example.monoproj.sample_board.entity;

import com.example.monoproj.account_profile.entity.AccountProfile;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@NoArgsConstructor

public class SampleBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sampleBoardId;

    @Setter
    private String title;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "account_profile_id", nullable = false)
    @Setter
    private String writer;

    @Setter
    private String content;

    public SampleBoard(String title, String writer, String content) {
        this.title = title;
        this.writer = writer;
        this.content = content;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @CreationTimestamp
    private LocalDateTime createdDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @UpdateTimestamp
    private LocalDateTime updatedDate;
}
