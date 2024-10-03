package org.project.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.entite.User;
import org.project.service.UserService;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserServlet", value = "/users")
public class UserServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idP = request.getParameter("id");
        if (idP != null){
            int id = Integer.parseInt(idP);
            User user = userService.getUserById(id);
            if (user != null) {
                request.setAttribute("user", user);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/views/editUser.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect("users");
            }
        }else {
            List<User> users = userService.getAllUsers();
            System.out.println(users);
            request.setAttribute("users", users);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/user.jsp");
            dispatcher.forward(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("create".equals(action)) {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            userService.createUser(user);
        } else if ("delete".equals(action)) {
            Long id = Long.valueOf(request.getParameter("id"));
            userService.deleteUser(id);
        } else if ("update".equals(action)) {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            int id = Integer.parseInt(request.getParameter("id"));
            User existingUser = userService.getUserById(id);
            if (existingUser != null) {
                existingUser.setId(id);
                existingUser.setName(name);
                existingUser.setEmail(email);
                userService.updateUser(existingUser);
            }
            userService.updateUser(existingUser);
        }
        response.sendRedirect("users");

    }

}
