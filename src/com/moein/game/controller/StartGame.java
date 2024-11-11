package com.moein.game.controller;


import com.moein.game.endpoint.WebSocketEndpoint;
import com.moein.game.entity.Game;
import com.moein.game.entity.GameMove;
import com.moein.game.entity.Player;
import com.moein.game.entity.User;
import com.moein.game.service.GameService;
import com.moein.game.service.UserService;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import java.io.IOException;

@WebServlet("/game/start.do")
public class StartGame extends HttpServlet {

    @EJB
    private GameService gameService;

    @EJB
    private UserService userService;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            boolean isVsMachine = Boolean.valueOf(req.getParameter("isVsMachine"));
            String playerOneName = req.getParameter("playerOneName");
            GameMove playerOneMove = GameMove.valueOf(req.getParameter("playerOneMove"));
            String username = req.getParameter("username");
            User user = userService.findUsersByUsername(username).get(0);

            Player playerOne = new Player().builder()
                    .playerName(playerOneName)
                    .gameMove(playerOneMove)
                    .user(user)
                    .build();
            Game savedGame = gameService.startGame(playerOne);

            if (isVsMachine) {
                Player playerTwo = new Player().builder().playerName("machine").build();
                Game finishedGame = gameService.finishGame(savedGame.getGameId(), playerTwo);
                WebSocketEndpoint.broadcastGameUpdate(finishedGame);
            }

            resp.sendRedirect("/game/findAll.do");
        }catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            resp.sendError(701);
        }
    }
}
