package com.moein.game.service;

import com.moein.game.entity.Game;
import com.moein.game.entity.GameChat;
import com.moein.game.repository.CrudRepository;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.*;
import java.util.*;
import java.util.stream.Collectors;

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

    public List<GameChat> findAllGameChatByGame(Game game) {
        Map<String, Object> params = new HashMap<>();
        params.put("content", game);
        List<GameChat> gameChats = crudRepository.findAll(GameChat.class,"entity.game=:content", params);
        return gameChats;
    }
    public List<GameChat> findAllGameChatByGame(Game game, int firstResultPage, int maxSizePage) {
        Map<String, Object> params = new HashMap<>();
        params.put("content", game);
        List<GameChat> gameChats = crudRepository.findAllPaging(GameChat.class,"entity.game=:content", params, "gameChatId", firstResultPage, maxSizePage);
        //gameChats.sort(Comparator.comparing(GameChat::getGameChatId));
        //.stream().sorted(Comparator.comparing(GameChat::getGameChatId)).collect(Collectors.toList());
        Collections.reverse(gameChats);
        return gameChats;
    }

    public List<GameChat> findAllWithChildrenPaging(Game game, int firstResultPage, int maxSizePage) {
        Map<String, Object> params = new HashMap<>();
        params.put("content", game);
        List<GameChat> gameChats = crudRepository.findAllWithChildrenPaging(GameChat.class, "game", "user", "entity.game=:content", params, "gameChatId", firstResultPage, maxSizePage);
        //crudRepository.findAllPaging(GameChat.class,"entity.game=:content", params, "gameChatId", firstResultPage, maxSizePage);
        //gameChats.sort(Comparator.comparing(GameChat::getGameChatId));
        //.stream().sorted(Comparator.comparing(GameChat::getGameChatId)).collect(Collectors.toList());
        Collections.reverse(gameChats);
        return gameChats;
    }
}
