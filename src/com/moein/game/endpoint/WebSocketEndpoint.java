package com.moein.game.endpoint;

import com.moein.game.entity.Game;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/game-updates")
public class WebSocketEndpoint {
    private static Map<Session, String> sessions = new ConcurrentHashMap<>();
    //private static Set<Session> sessions = ConcurrentHashMap.newKeySet();
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("online");
    }
    @OnMessage
    public void onMessage(Session session, String username) {
        sessions.put(session, username);
        //sessions.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }

    public static void broadcastGameUpdate(Game game) throws IOException, EncodeException {
        String usernameOne = game.getPlayerOne().getUser().getUsername();
        String usernameTwo = Objects.isNull(game.getPlayerTwo().getUser()) ? "" : game.getPlayerTwo().getUser().getUsername();
        String result = "Player One Name: "+game.getPlayerOne().getPlayerName()+"\n"+"Player One Move: "+game.getPlayerOne().getGameMove().toString()+"\n"+
                        "Player Two Name: "+game.getPlayerTwo().getPlayerName()+"\n"+"Player Two Move: "+game.getPlayerTwo().getGameMove().toString()+"\n"+
                        "Game Result: "+game.getGameResult().toString();
        for (Map.Entry<Session, String> entry : sessions.entrySet()) {
            if (entry.getValue().equals(usernameOne) || entry.getValue().equals(usernameTwo)) {
                entry.getKey().getBasicRemote().sendText(result);
            }
        }
        /*for (Session session : sessions) {
            String result = "Player One Name: "+game.getPlayerOne().getPlayerName()+"\n"+"Player One Move: "+game.getPlayerOne().getGameMove().toString()+"\n"+
                            "Player Two Name: "+game.getPlayerTwo().getPlayerName()+"\n"+"Player Two Move: "+game.getPlayerTwo().getGameMove().toString()+"\n"+
                            "Game Result: "+game.getGameResult().toString();
            session.getBasicRemote().sendText(result);
        }*/
    }
    public static void broadcastGameUpdate() throws IOException {
        for (Map.Entry<Session, String> entry : sessions.entrySet()) {
            entry.getKey().getBasicRemote().sendText("");             
        }
    }
}