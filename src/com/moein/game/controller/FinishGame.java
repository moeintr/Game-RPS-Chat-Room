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
import java.io.IOException;

@WebServlet("/game/finish.do")
public class FinishGame extends HttpServlet {

    @EJB
    private GameService gameService;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            GameMove gameMove = GameMove.valueOf(req.getParameter("playerTwoMove"));
            Game game = gameService.finishGame(Integer.parseInt(req.getParameter("gameId")),req.getParameter("playerTwoName"), gameMove);
            resp.sendRedirect("/game/findAll.do");
            WebSocketEndpoint.broadcastGameUpdate(game);
        }catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            resp.sendError(701);
        }
    }
}
