package com.moein.game.controller;


import com.moein.game.endpoint.WebSocketEndpoint;
import com.moein.game.entity.Game;
import com.moein.game.entity.GameChat;
import com.moein.game.entity.User;
import com.moein.game.service.GameChatService;
import com.moein.game.service.GameService;
import com.moein.game.service.UserService;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/game/saveChat.do")
public class SaveGameChat extends HttpServlet {
    @EJB
    private GameChatService gameChatService;
    @EJB
    private UserService userService;
    @EJB
    private GameService gameService;
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String username = req.getParameter("username");
            User user = userService.findUsersByUsername(username).get(0);

            int gameId = Integer.parseInt(req.getParameter("gameId"));
            Game game = gameService.findGameById(gameId);

            String message = req.getParameter("message");

            GameChat gameChat = GameChat.builder()
                    .user(user)
                    .game(game)
                    .message(message).build();

            gameChatService.saveGameChat(gameChat);

            WebSocketEndpoint.broadcastGameUpdate();

            req.getSession().setAttribute("gameId", gameId);
            req.getSession().setAttribute("maxSizeRows", req.getParameter("maxSizeRows"));
            resp.sendRedirect("/game/findAllPagingChats.do");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            resp.sendError(701);
        }
    }
}
