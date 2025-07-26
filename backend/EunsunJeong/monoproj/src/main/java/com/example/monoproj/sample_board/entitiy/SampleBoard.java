package com.example.monoproj.sample_board.entitiy;

import com.example.monoproj.account_profile.entity.AccountProfile;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@NoArgsConstructor
//@RequiredArgsConstructor
public class SampleBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Setter
    private String title;

    @ManyToOne
    @JoinColumn(name = "account_profile_id", nullable = false)
    private AccountProfile writer;

    @Setter
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm",
            timezone = "Asia/Seoul")
    @CreationTimestamp
    private LocalDateTime createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm",
            timezone = "Asia/Seoul")
    @UpdateTimestamp
    private LocalDateTime updateDate;

    //생성자
    public void SampleBoard(String title, AccountProfile writer, String content){
        this.title = title;
        this.writer = writer;
        this.content = content;
    }
}
