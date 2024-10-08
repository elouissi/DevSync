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
import org.project.entite.Tag;
import org.project.entite.Task;
import org.project.entite.User;
import org.project.service.TagService;
import org.project.service.TaskService;
import org.project.service.UserService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "TaskServlet", value = "/tasks")
public class TaskServlet extends HttpServlet {
    private TaskService taskService;
    private UserService userService;
    private TagService tagService;

    @Override
    public void init() {
        taskService = new TaskService();
        userService = new UserService();
        tagService = new TagService();
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
            List<Tag> tags = tagService.getALlTags();
            System.out.println(tasks);
            request.setAttribute("tasks", tasks);
            request.setAttribute("tags", tags);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/task.jsp");
            dispatcher.forward(request, response);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        if ("create".equals(action)) {
            String titre = request.getParameter("titre");
            String description = request.getParameter("description");
            String created_user_id = request.getParameter("created_user_id");
            String assigned_user_id = request.getParameter("assigned_user_id");
            TypeStatus status = TypeStatus.valueOf(request.getParameter("status"));
            LocalDate date_debut = LocalDate.parse(request.getParameter("date_debut"));
            LocalDate date_fin = LocalDate.parse(request.getParameter("date_fin"));
            if (LocalDate.now().plusDays(3).isAfter(date_debut) )
            if (date_debut.isAfter(date_fin)) {
                if (date_debut.isAfter(date_fin)) {
                    request.getSession().setAttribute("error", "La date de début ne peut pas être après la date de fin.");
                    response.sendRedirect("tasks");
                    return;
                }
                response.sendRedirect("tasks");
                return;
            }else {
                request.getSession().setAttribute("error", "La date que vous avez entrée il faut etre apres 3 jour la date d'aujourd'hui");
            }
            String selectedTagIds = request.getParameter("selected_tags");
            if (selectedTagIds != null && !selectedTagIds.isEmpty()) {
                String[] tagIdsArray = selectedTagIds.split(",");
                List<Tag> selectedTags = new ArrayList<>();
                for (String tagId : tagIdsArray) {
                    Tag tag = tagService.getById(Integer.parseInt(tagId));
                    selectedTags.add(tag);
                }
                System.out.println(selectedTags);
            Task task = new Task();
            task.setTitre(titre);
            task.setTags(selectedTags);
            task.setDescription(description);
            task.setAssignedTo(userService.getUserById(Integer.parseInt(assigned_user_id)));
            task.setCreatedBy(userService.getUserById(Integer.parseInt(created_user_id)));
            task.setStatus(status);
            task.setDateDebut(date_debut);
            task.setDateFin(date_fin);
            taskService.createTask(task);
            response.sendRedirect("tasks");
        }else {
                request.getSession().setAttribute("erreur","entrez les tags" );
                response.sendRedirect("taks");
            }

        } else if ("delete".equals(action)) {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("currentUser") == null) {
                response.sendRedirect("users");
                return;
            }
            int taskId = Integer.parseInt(request.getParameter("id"));
            taskService.deleteTask(taskId);
            response.sendRedirect("tasks");


        }


    }
}
