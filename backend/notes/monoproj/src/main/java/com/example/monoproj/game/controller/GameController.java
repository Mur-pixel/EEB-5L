package com.example.monoproj.game.controller;

import com.example.monoproj.game.service.GameService;
import com.example.monoproj.redis_cache.service.RedisCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;
    private final RedisCacheService redisCacheService;

    @GetMapping("/start")
    public Long gameStart(@RequestHeader("Authorization") String authorizationHeader) {
        log.info("gameStart()");
        log.info("authorizationHeader -> {}", authorizationHeader);

        String token = authorizationHeader.replace("Bearer ", "").trim();
        Long accountId = redisCacheService.getValueByKey(token, Long.class);
        log.info("accountId -> {}", accountId);

        return gameService.start(accountId);
    }
}
