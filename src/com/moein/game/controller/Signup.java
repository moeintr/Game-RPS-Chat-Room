package com.moein.game.controller;

import com.moein.game.entity.Role;
import com.moein.game.entity.User;
import com.moein.game.security.MD5;
import com.moein.game.service.UserService;
import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet("/user/signup.do")
public class Signup extends HttpServlet {
    @EJB
    private UserService userService;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            User user = new User().builder()
                    .username(username)
                    .password(MD5.getHash(password))
                    .userRoles(Arrays.asList(new Role().builder()
                            .username(username)
                            .roleName("user")
                            .build()))
                    .build();
            userService.saveUser(user);
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            resp.sendError(405);
        }
    }
}
