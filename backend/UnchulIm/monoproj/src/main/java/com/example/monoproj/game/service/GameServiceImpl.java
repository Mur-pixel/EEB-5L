package com.example.monoproj.game.service;

import com.example.monoproj.game.entity.Game;
import com.example.monoproj.game.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;

    @Override
    public Long start() {
        Game game = new Game();
        System.out.println("game: " + game);
        Game savedGame = gameRepository.save(game);
        return savedGame.getId();
    }
}
//do u know 발키뤼? 잇츠 굿?뽈유