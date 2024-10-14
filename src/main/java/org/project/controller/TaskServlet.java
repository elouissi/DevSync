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
import org.project.entite.Tag;
import org.project.entite.Task;
import org.project.entite.User;
import org.project.scheduler.TaskScheduler;
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
            List<Tag> tags = tagService.getALlTags();
            if (task != null) {
                request.setAttribute("task", task);
                request.setAttribute("tags",tags);
                request.setAttribute("users", users);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/views/editTask.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect("tasks");
            }
        } else {
//            TaskScheduler scheduler = new TaskScheduler();
//            scheduler.startScheduler();
            List<Task> tasks = taskService.getALlTasks();
            List<Tag> tags = tagService.getALlTags();
            request.setAttribute("tasks", tasks);
            request.setAttribute("tags", tags);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/task.jsp");
            dispatcher.forward(request, response);
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        if ("create".equals(action)) {
            System.out.println("Voilà, c'est la fonction de création");

            String titre = request.getParameter("titre");
            String description = request.getParameter("description");
            String createdUserIdParam = request.getParameter("created_user_id");
            String assignedUserIdParam = request.getParameter("assigned_user_id");
            String statusParam = request.getParameter("status");
            String dateDebutParam = request.getParameter("date_debut");
            String dateFinParam = request.getParameter("date_fin");
            String selectedTagIds = request.getParameter("selected_tags");

            if (titre == null || description == null || createdUserIdParam == null || assignedUserIdParam == null || statusParam == null || dateDebutParam == null || dateFinParam == null) {
                request.getSession().setAttribute("error", "Tous les champs sont obligatoires.");
                response.sendRedirect("tasks");
                return;
            }

            try {
                TypeStatus status = TypeStatus.valueOf(statusParam);
                LocalDate dateDebut = LocalDate.parse(dateDebutParam);
                LocalDate dateFin = LocalDate.parse(dateFinParam);

                if (LocalDate.now().plusDays(3).isAfter(dateDebut)) {
                    request.getSession().setAttribute("error", "La date de début doit être au moins 3 jours après aujourd'hui.");
                    response.sendRedirect("tasks");
                    return;
                }

                if (dateDebut.isAfter(dateFin)) {
                    request.getSession().setAttribute("error", "La date de début ne peut pas être après la date de fin.");
                    response.sendRedirect("tasks");
                    return;
                }

                User createdUser = userService.getUserById(Integer.parseInt(createdUserIdParam));
                User assignedUser = userService.getUserById(Integer.parseInt(assignedUserIdParam));
                if (createdUser == null || assignedUser == null) {
                    request.getSession().setAttribute("error", "Les utilisateurs sélectionnés sont invalides.");
                    response.sendRedirect("tasks");
                    return;
                }


                List<Tag> selectedTags = new ArrayList<>();
                if (selectedTagIds != null && !selectedTagIds.isEmpty()) {
                    String[] tagIdsArray = selectedTagIds.split(",");
                    for (String tagId : tagIdsArray) {
                        Tag tag = tagService.getById(Integer.parseInt(tagId));
                        if (tag != null) {
                            selectedTags.add(tag);
                        }
                    }
                }

                if (selectedTags.isEmpty()) {
                    request.getSession().setAttribute("error", "Veuillez sélectionner au moins un tag.");
                    response.sendRedirect("tasks");
                    return;
                }

                Task task = new Task();
                task.setTitre(titre);
                task.setDescription(description);
                task.setCreatedBy(createdUser);
                task.setAssignedTo(assignedUser);
                task.setStatus(status);
                task.setDateDebut(dateDebut);
                task.setDateFin(dateFin);
                task.setTags(selectedTags);

                taskService.createTask(task);
                response.sendRedirect("tasks");
            } catch (Exception e) {
                e.printStackTrace();
                request.getSession().setAttribute("error", "Une erreur est survenue lors de la création de la tâche.");
                response.sendRedirect("tasks");
            }
        }
        else if ("delete".equals(action)) {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("currentUser") == null) {
                response.sendRedirect("users");
                return;
            }
            User currentUser = (User) request.getSession().getAttribute("currentUser");
            int taskId = Integer.parseInt(request.getParameter("taskId"));
            Task task = taskService.getById(taskId);
            taskService.deleteTask(taskId);
            if (!(task.getCreatedBy().getId() == currentUser.getId())){
                currentUser.setJeton_Annuel(currentUser.getJeton_Annuel() - 1);
                userService.updateUser(currentUser);
            }
            response.sendRedirect("tasks");

        }
        else if ("update".equals(action)) {
            String titre = request.getParameter("titre");
            String description = request.getParameter("description");
            String created_user_id = request.getParameter("created_user_id");
            String assigned_user_id = request.getParameter("assigned_user_id");
            TypeStatus status = TypeStatus.valueOf(request.getParameter("status"));
            LocalDate date_debut = LocalDate.parse(request.getParameter("date_debut"));
            LocalDate date_fin = LocalDate.parse(request.getParameter("date_fin"));
            String selectedTagIds = request.getParameter("selected_tags");
            int id = Integer.parseInt(request.getParameter("id"));
            Task existingTask = taskService.getById(id);
            if (existingTask != null) {
                existingTask.setId(id);
                existingTask.setTitre(titre);
                existingTask.setDescription(description);
                existingTask.setCreatedBy(userService.getUserById(Integer.parseInt(created_user_id)));
                existingTask.setAssignedTo(userService.getUserById(Integer.parseInt(assigned_user_id)));
                existingTask.setDateDebut(date_debut);
                existingTask.setDateFin(date_fin);
                if (selectedTagIds != null && !selectedTagIds.isEmpty()) {
                    String[] tagIdsArray = selectedTagIds.split(",");
                    List<Tag> selectedTags = new ArrayList<>();
                    for (String tagId : tagIdsArray) {
                        Tag tag = tagService.getById(Integer.parseInt(tagId));
                        selectedTags.add(tag);
                    }
                    existingTask.setTags(selectedTags);
                    taskService.updateTask(existingTask);
                }

            }
        }


    }
}
