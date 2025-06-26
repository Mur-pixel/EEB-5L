package com.example.monoproj.dice.service;

import com.example.monoproj.account.entity.Account;
import com.example.monoproj.account.repository.AccountRepository;
import com.example.monoproj.dice.controller.request_form.DiceRollResultRequestForm;
import com.example.monoproj.dice.entity.Dice;
import com.example.monoproj.dice.repository.DiceRepository;
import com.example.monoproj.dice.service.request.DiceRollResultRequest;
import com.example.monoproj.game.entity.Game;
import com.example.monoproj.game.repository.GameRepository;
import com.example.monoproj.game.service.request.GameFindRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiceServiceImpl implements DiceService {
    final private DiceRepository diceRepository;
    final private GameRepository gameRepository;
    final private AccountRepository accountRepository;

    @Override
    @Transactional
    public Boolean saveRollResult(
            DiceRollResultRequest request,
            GameFindRequest gameFindRequest,
            Long accountId) {
        log.info("saveRollResult(): " + request);

        Optional<Game> maybeGame = gameRepository.findById(gameFindRequest.getGameId());
        if (maybeGame.isEmpty()) {
            log.info("saveRollResult(): Game not found");
            return false;
        }

        Game game = maybeGame.get();
        Optional<Account> maybeAccount = accountRepository.findById(accountId);
        if (maybeAccount.isEmpty()) {
            log.info("saveRollResult(): Account not found");
            return false;
        }

        Account account = maybeAccount.get();
        game.setAccount(account);

        Dice savedDice = diceRepository.save(request.toDice());
        game.addDice(savedDice);

        return savedDice != null;
    }
}
