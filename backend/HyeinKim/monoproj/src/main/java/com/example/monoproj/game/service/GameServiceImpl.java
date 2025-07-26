package com.example.monoproj.game.service;

import com.example.monoproj.account.entity.Account;
import com.example.monoproj.account.repository.AccountRepository;
import com.example.monoproj.game.entity.Game;
import com.example.monoproj.game.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor // 초기화 되지않은 final 필드나, @NonNull 이 붙은 필드 -> 생성자를 생성 (singleton)
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final AccountRepository accountRepository;

    @Override
    public Long start(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 계정입니다."));

        Game game = new Game();
        game.setAccount(account);

        Game savedGame = gameRepository.save(game);
        log.info("게임 생성 완료 - gameId: {}, userId: {}", savedGame.getId(), accountId);

        return savedGame.getId();
    }
}