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
import java.io.IOException;

@WebServlet("/game/finish.do")
public class FinishGame extends HttpServlet {

    @EJB
    private GameService gameService;

    @EJB
    private UserService userService;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int gameId = Integer.parseInt(req.getParameter("gameId"));
            String playerTwoName = req.getParameter("playerTwoName");
            GameMove playerTwoMove = GameMove.valueOf(req.getParameter("playerTwoMove"));
            String username = req.getParameter("username");
            User user = userService.findUsersByUsername(username).get(0);
            Player playerTwo = new Player().builder()
                    .playerName(playerTwoName)
                    .gameMove(playerTwoMove)
                    .user(user)
                    .build();
            Game game = gameService.finishGame(gameId, playerTwo);
            resp.sendRedirect("/game/findAll.do");
            WebSocketEndpoint.broadcastGameUpdate(game);
        }catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            resp.sendError(701);
        }
    }
}
