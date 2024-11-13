package com.moein.game.controller;

import com.moein.game.entity.GameChat;
import com.moein.game.service.GameChatService;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/game/findAllGameChats.do")
public class FindAllGameChats extends HttpServlet {
    @EJB
    private GameChatService gameChatService;
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int gameId = Integer.parseInt(req.getParameter("gameId"));
            List<GameChat> gameChats = gameChatService.findAllGameChatByGameId(gameId);
            req.setAttribute("listGameChats", gameChats);
            req.getRequestDispatcher("/game/rock-paper-scissors.jsp").forward(req, resp);
        }catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            resp.sendError(701);
        }
    }
}
