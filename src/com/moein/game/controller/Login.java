package com.moein.game.controller;

import com.moein.game.security.MD5;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/login.do")
public class Login extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            req.login(username, MD5.getHash(password));
            resp.sendRedirect("/game/findAll.do");
        } catch (Exception e) {
            resp.sendRedirect("/login-error.jsp");
        }
    }
}
