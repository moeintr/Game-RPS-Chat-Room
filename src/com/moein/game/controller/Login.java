package com.moein.game.controller;

import com.moein.game.entity.User;
import com.moein.game.security.MD5;
import com.moein.game.service.UserService;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/login.do")
public class Login extends HttpServlet {
    @EJB
    private UserService userService;
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String username = req.getParameter("username");
            String password = MD5.getHash(req.getParameter("password"));
            req.login(username, password);
            User user = userService.findUserByUsernameAndPassword(username, password);
            req.setAttribute("user", user);
            resp.sendRedirect("/game/findAll.do");
        } catch (Exception e) {
            resp.sendRedirect("/login-error.jsp");
        }
    }
}
