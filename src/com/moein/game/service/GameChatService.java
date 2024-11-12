package com.moein.game.service;

import com.moein.game.entity.Game;
import com.moein.game.entity.GameChat;
import com.moein.game.entity.User;
import com.moein.game.repository.CrudRepository;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class GameChatService {
    @EJB
    private CrudRepository<GameChat, Long> crudRepository;
    @Resource
    private UserTransaction transaction;

    public void saveGameChat(GameChat gameChat) throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        transaction.begin();
        crudRepository.save(gameChat);
        transaction.commit();
    }

    public List<GameChat> findAllGameChatByGameId(int gameId) {
        Map<String, Object> params = new HashMap<>();
        params.put("content", gameId);
        List<GameChat> gameChats = crudRepository.findAll(GameChat.class,"entity.game_id=:content", params);
        return gameChats;
    }
}
