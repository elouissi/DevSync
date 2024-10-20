package org.project.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.Enum.TypeRole;
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

        if (idP != null) {
            int id = Integer.parseInt(idP);
            User user = userService.getUserById(id);
            if (user != null) {
                request.setAttribute("user", user);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/views/editUser.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect("users");
            }
        } else {
            List<User> users = userService.getAllUsers();
            request.setAttribute("users", users);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/user.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("create".equals(action)) {
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String mot_de_pas = request.getParameter("mot_de_pass");
                TypeRole role = TypeRole.valueOf(request.getParameter("role"));

                User user = new User();
                user.setName(name);
                user.setEmail(email);
                user.setMot_de_pass(mot_de_pas);
                user.setRole(role);

                userService.createUser(user);

            } else if ("delete".equals(action)) {
                Long id = Long.valueOf(request.getParameter("id"));
                userService.deleteUser(id);

            } else if ("update".equals(action)) {
                String name = request.getParameter("name");
                String email = request.getParameter("email");
                String mot_de_pas = request.getParameter("mot_de_pas");
                TypeRole role = TypeRole.valueOf(request.getParameter("role"));
                int id = Integer.parseInt(request.getParameter("id"));

                User user = new User();
                user.setId(id);
                user.setName(name);
                user.setEmail(email);
                user.setMot_de_pass(mot_de_pas);
                user.setRole(role);

                userService.updateUser(user);
            }

            response.sendRedirect("users");

        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/error.jsp");
            dispatcher.forward(request, response);
        }
    }
}
