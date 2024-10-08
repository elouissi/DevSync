package org.project.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.project.entite.Task;
import org.project.entite.User;
import org.project.service.TaskService;
import org.project.service.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "TaskServlet", value = "/tasks")
public class TaskServlet extends HttpServlet {
    private TaskService taskService;
    private UserService userService;

    @Override
    public void init() {
        taskService = new TaskService();
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect("users");
            return;
        }

        String idP = request.getParameter("id");

        List<User> users = userService.getAllUsers();
        request.setAttribute("users", users);

        if (idP != null) {
            int id = Integer.parseInt(idP);
            Task task = taskService.getById(id);
            if (task != null) {
                request.setAttribute("task", task);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/views/editTask.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect("tasks");
            }
        } else {
            List<Task> tasks = taskService.getALlTasks();
            request.setAttribute("tasks", tasks);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/task.jsp");
            dispatcher.forward(request, response);
        }
    }
}
