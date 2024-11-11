package com.moein.game.service;

import com.moein.game.entity.Game;
import com.moein.game.entity.GameMove;
import com.moein.game.entity.GameResult;
import com.moein.game.entity.Player;
import com.moein.game.exception.NotFoundException;
import com.moein.game.repository.CrudRepository;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class GameService {
    @EJB
    private CrudRepository<Game, Integer> crudRepository;
    @Resource
    private UserTransaction transaction;
    
    public Game startGame(Player playerOne) throws Exception {
        Game game = new Game().builder()
                              .playerOne(playerOne)
                              .startGameDate(new Date())
                              .build();
        transaction.begin();
        Game savedGame = crudRepository.saveAndFlush(game);
        transaction.commit();
        return savedGame;
    }

    public Game finishGame(int gameId, Player playerTwo) throws NotFoundException, SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        Game game = findGameById(gameId);

        if (playerTwo.getPlayerName().equals("machine") || Objects.isNull(playerTwo.getGameMove())){  //means play vs machine
            int pick = new Random().nextInt(GameMove.values().length);
            playerTwo.setGameMove(GameMove.values()[pick]);
        }

        game.setPlayerTwo(playerTwo);
        game.setGameResult(compareMoves(game.getPlayerOne().getGameMove(), playerTwo.getGameMove()));
        game.setFinishGameDate(new Date());

        transaction.begin();
        crudRepository.update(game);
        transaction.commit();

        return game;
    }

    private GameResult compareMoves(GameMove movePlayerOne, GameMove movePlayerTwo) {
        if (movePlayerOne.equals(movePlayerTwo))
        {
            return GameResult.EQUAL;
        } else if (movePlayerOne.equals(GameMove.PAPER) && movePlayerTwo.equals(GameMove.ROCK) ||
                   movePlayerOne.equals(GameMove.ROCK) && movePlayerTwo.equals(GameMove.SCISSORS) ||
                   movePlayerOne.equals(GameMove.SCISSORS) && movePlayerTwo.equals(GameMove.PAPER))
        {
            return GameResult.WIN_ONE;
        }else
        {
            return GameResult.WIN_TWO;
        }
    }

    public Game findGameById(int gameId) throws NotFoundException {
        Game game = crudRepository.findOne(Game.class, gameId);
        if(Objects.isNull(game)) {
            throw new NotFoundException("Game not found");
        }
        return game;
    }
    
    public List<Game> findAllGames() {
        return crudRepository.findAll(Game.class);
    }

}
