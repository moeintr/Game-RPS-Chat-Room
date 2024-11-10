package com.moein.game.endpoint;

import com.moein.game.entity.Game;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/game-updates")
public class WebSocketEndpoint {
    private static Set<Session> sessions = ConcurrentHashMap.newKeySet();

    @OnMessage
    public static void onMessage(Session session, String msg) {
        sessions.add(session);
    }

    @OnClose
    public static void onClose(Session session) {
        sessions.remove(session);
    }

    public static void broadcastGameUpdate(Game game) throws IOException, EncodeException {
        for (Session session : sessions) {
            String result = "Player One Name: "+game.getPlayerOne().getPlayerName()+"\n"+"Player One Move: "+game.getPlayerOne().getGameMove().toString()+"\n"+
                            "Player Two Name: "+game.getPlayerTwo().getPlayerName()+"\n"+"Player Two Move: "+game.getPlayerTwo().getGameMove().toString()+"\n"+
                            "Game Result: "+game.getGameResult().toString();
            session.getBasicRemote().sendText(result);
        }
    }
}