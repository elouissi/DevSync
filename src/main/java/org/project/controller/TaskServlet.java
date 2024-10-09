package org.project.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.project.Enum.TypeRole;
import org.project.Enum.TypeStatus;
import org.project.Util.PasswordUtil;
import org.project.entite.Task;
import org.project.entite.User;
import org.project.service.TaskService;
import org.project.service.UserService;

import java.io.IOException;
import java.time.LocalDate;
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
            System.out.println(tasks);
            request.setAttribute("tasks", tasks);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/task.jsp");
            dispatcher.forward(request, response);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        if ("create".equals(action)) {
            String titre = request.getParameter("titre");
            String description = request.getParameter("description");
            String created_user_id = request.getParameter("created_user_id");
            String assigned_user_id = request.getParameter("assigned_user_id");
            TypeStatus status = TypeStatus.valueOf(request.getParameter("status"));
            LocalDate date_debut = LocalDate.parse(request.getParameter("date_debut"));
            LocalDate date_fin = LocalDate.parse(request.getParameter("date_fin"));
            Task task = new Task();
            task.setTitre(titre);
            task.setDescription(description);
            task.setAssignedTo(userService.getUserById(Integer.parseInt(assigned_user_id)));
            task.setCreatedBy(userService.getUserById(Integer.parseInt(created_user_id)));
            task.setStatus(status);
            task.setDateDebut(date_debut);
            task.setDateFin(date_fin);
            taskService.createTask(task);
            response.sendRedirect("tasks");
        }

        }
}
