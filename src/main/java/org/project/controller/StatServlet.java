package org.project.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.project.entite.Task;
import org.project.entite.User;
import org.project.service.TaskService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "StatServlet",value = "/stats")
public class StatServlet extends HttpServlet {
    private TaskService taskService = new TaskService();
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect("users");
            return;
        }
        User currentUser = (User) session.getAttribute("currentUser");


        String filter = request.getParameter("filter");
        if (filter == null) {
            filter = "semaine";
        }

        List<Task> managerTasks = taskService.getTasksForManager(currentUser, filter);
        request.setAttribute("tasks", managerTasks);
        request.getRequestDispatcher("views/stats.jsp").forward(request, response);



    }
}
