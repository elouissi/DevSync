package org.project.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.project.Enum.TypeStatus;
import org.project.entite.Tag;
import org.project.entite.Task;
import org.project.entite.User;
import org.project.service.TagService;
import org.project.service.TaskService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "StatServlet", value = "/stats")
public class StatServlet extends HttpServlet {
    private TaskService taskService = new TaskService();
    private TagService tagService = new TagService();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect("users");
            return;
        }
        User currentUser = (User) session.getAttribute("currentUser");

        List<Task> allTasks = taskService.getALlTasks();
        List<Tag> allTags = tagService.getALlTags();
        request.setAttribute("allTags", allTags);

        String selectedTagsParam = request.getParameter("selected_tags");
        List<Task> filteredTasks;

        if (selectedTagsParam != null && !selectedTagsParam.isEmpty()) {
            String[] selectedTagIds = selectedTagsParam.split(",");

            filteredTasks = allTasks.stream()
                    .filter(task -> task.getTags().stream()
                            .anyMatch(tag -> Arrays.asList(selectedTagIds).contains(String.valueOf(tag.getId()))))
                    .collect(Collectors.toList());
        } else {
            filteredTasks = allTasks;

        }
        String startDateParam = request.getParameter("start_date");
        String endDateParam = request.getParameter("end_date");



        if ((startDateParam != null && !startDateParam.isEmpty()) ||
                (endDateParam != null && !endDateParam.isEmpty())) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDate = startDateParam != null && !startDateParam.isEmpty() ?
                    LocalDate.parse(startDateParam, formatter) : null;
            LocalDate endDate = endDateParam != null && !endDateParam.isEmpty() ?
                    LocalDate.parse(endDateParam, formatter) : null;

            filteredTasks = filteredTasks.stream()
                    .filter(task -> {
                        LocalDate taskDate = task.getDateDebut();
                        boolean isAfterStart = (startDate == null || !taskDate.isBefore(startDate));
                        boolean isBeforeEnd = (endDate == null || !taskDate.isAfter(endDate));
                        return isAfterStart && isBeforeEnd;
                    })
                    .collect(Collectors.toList());

            long totale = filteredTasks.stream()
                    .filter(task -> {
                        LocalDate taskDate = task.getDateDebut();
                        boolean isAfterStart = (startDate == null || !taskDate.isBefore(startDate));
                        boolean isBeforeEnd = (endDate == null || !taskDate.isAfter(endDate));
                        return isAfterStart && isBeforeEnd;
                    })
                    .count();
            System.out.println("le totale:"+totale);
        } else {
            filteredTasks = allTasks;

        }

        request.setAttribute("tasks", filteredTasks);
        long totalTasks = filteredTasks.size();
        if (totalTasks > 0) {
            long tasksEn_cours = filteredTasks.stream().filter(t -> t.getStatus() == TypeStatus.en_cours).count();
            long tasksEn_attend = filteredTasks.stream().filter(t -> t.getStatus() == TypeStatus.en_attend).count();
            long tasksTermine = filteredTasks.stream().filter(t -> t.getStatus() == TypeStatus.termine).count();
            long tasksInComplete = filteredTasks.stream().filter(t -> t.getStatus() == TypeStatus.incomplete).count();

            double percentageEn_cours = (double) tasksEn_cours / totalTasks * 100;
            double percentageEn_attend = (double) tasksEn_attend / totalTasks * 100;
            double percentageTermine = (double) tasksTermine / totalTasks * 100;
            double percentageInComplete = (double) tasksInComplete / totalTasks * 100;

            request.setAttribute("percentageEn_cours", percentageEn_cours);
            request.setAttribute("percentageEn_attend", percentageEn_attend);
            request.setAttribute("percentageTermine", percentageTermine);
            request.setAttribute("percentageInComplete", percentageInComplete);
        } else {
            request.setAttribute("percentageEn_cours", 0.0);
            request.setAttribute("percentageEn_attend", 0.0);
            request.setAttribute("percentageTermine", 0.0);
            request.setAttribute("percentageInComplete", 0.0);
        }


        request.getRequestDispatcher("views/stats.jsp").forward(request, response);
    }
}