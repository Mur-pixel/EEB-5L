package com.example.monoproj.game_chip.service.request;

import com.example.monoproj.account.entity.Account;
import com.example.monoproj.game_chip.entity.GameChip;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class RegisterGameChipRequest {
    private final String title;
    private final String description;
    private final int price;

    private final Long accountId;

    public GameChip toGameChip(Account account) {
        return new GameChip(title, description, price, account);
    }
}
