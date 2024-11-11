package com.moein.game.controller;

import com.moein.game.entity.User;
import com.moein.game.service.GameService;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/game/findAll.do")
public class FindAllGames extends HttpServlet {

    @EJB
    private GameService gameService;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User user = (User) req.getAttribute("user");
            req.setAttribute("list", gameService.findAllGames());
            req.getRequestDispatcher("/game/rock-paper-scissors.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            resp.sendError(701);
        }
    }
}
