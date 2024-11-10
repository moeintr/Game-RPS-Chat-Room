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
    
    public Game startGame(String playerOneName, GameMove playerOneGameMove) throws Exception {
        Game game = new Game().builder()
                              .playerOne(new Player().builder()
                                            .playerName(playerOneName)
                                            .gameMove(playerOneGameMove).build())
                              .startGameDate(new Date())
                              .build();
        transaction.begin();
        Game savedGame = crudRepository.saveAndFlush(game);
        transaction.commit();
        return savedGame;
    }

    public Game finishGame(int gameId, String playerTwoName, GameMove playerTwoGameMove) throws NotFoundException, SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        Game game = crudRepository.findOne(Game.class,gameId);

        if (Objects.isNull(game)) {
            throw new NotFoundException("Game not found");
        }

        if (playerTwoName.equals("machine") || Objects.isNull(playerTwoGameMove)){  //means play vs machine
            int pick = new Random().nextInt(GameMove.values().length);
            playerTwoGameMove = GameMove.values()[pick];
            game.setPlayerTwo(new Player().builder().playerName(playerTwoName).gameMove(playerTwoGameMove).build());
            game.setGameResult(compareMoves(game.getPlayerOne().getGameMove(), playerTwoGameMove));
        }else { //means play vs human
            game.setPlayerTwo(new Player().builder().playerName(playerTwoName).gameMove(playerTwoGameMove).build());
            game.setGameResult(compareMoves(game.getPlayerOne().getGameMove(), playerTwoGameMove));
        }
        game.setFinishGameDate(new Date());

        transaction.begin();
        crudRepository.update(game);
        transaction.commit();

        return findGameById(gameId);
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
