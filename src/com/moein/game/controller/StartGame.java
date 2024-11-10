package com.moein.game.controller;


import com.moein.game.endpoint.WebSocketEndpoint;
import com.moein.game.entity.Game;
import com.moein.game.entity.GameMove;
import com.moein.game.service.GameService;
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

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            boolean isVsMachine = Boolean.valueOf(req.getParameter("isVsMachine"));
            GameMove gameMove = GameMove.valueOf(req.getParameter("playerOneMove"));
            Game savedGame = gameService.startGame(req.getParameter("playerOneName"), gameMove);
            if (isVsMachine) {
                Game finishedGame = gameService.finishGame(savedGame.getGameId(), "machine", null);
                WebSocketEndpoint.broadcastGameUpdate(finishedGame);
            }
            req.setAttribute("startdGameId", savedGame.getGameId());
            resp.sendRedirect("/game/findAll.do");
        }catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            resp.sendError(701);
        }
    }
}
