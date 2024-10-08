package org.project.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.project.entite.User;
import org.project.service.UserService;

import java.io.IOException;

@WebServlet( name = "loginUser",value = "/loginUser")
public class LoginServlet extends HttpServlet {

    UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, IOException, ServletException {
        String userId = request.getParameter("userId");

        User user = userService.getUserById(Integer.parseInt(userId));

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("currentUser", user);
            RequestDispatcher disp = request.getRequestDispatcher("/views/task.jsp");
            disp.forward(request,response);
        } else {
            response.sendRedirect("error.jsp");
        }
    }
}
