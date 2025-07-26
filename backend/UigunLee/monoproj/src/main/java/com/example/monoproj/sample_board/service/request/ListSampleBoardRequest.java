package com.example.monoproj.sample_board.service.request;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ListSampleBoardRequest {
    private int page;
    private int perPage;
}