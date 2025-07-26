package com.example.monoproj.sample_board.service.request;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSampleBoardRequest {
    private String title;
    private String content;
}