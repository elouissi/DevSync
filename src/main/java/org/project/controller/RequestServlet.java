package org.project.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.project.Enum.TypeRequest;
import org.project.entite.Request;
import org.project.entite.Task;
import org.project.entite.User;
import org.project.service.RequestService;
import org.project.service.TaskService;
import org.project.service.UserService;

import java.io.IOException;
import java.util.List;
@WebServlet(name = "RequestServlet", value = "/requests")

public class RequestServlet extends HttpServlet {
    private RequestService requestService;
    private TaskService taskService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        requestService = new RequestService();
        taskService = new TaskService();
        userService = new UserService();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect("users");
            return;
        }
        System.out.println("la requet a une direction correct2");
        List<Request> requests = requestService.getALlRequests();
        request.setAttribute("requests", requests);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/request.jsp");
        dispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String action = request.getParameter("action");


            if ("rejectCreate".equals(action)) {
                User currentUser = (User) request.getSession().getAttribute("currentUser");

                if (currentUser.getJeton_Monsuel() > 0) {
                    int taskId = Integer.parseInt(request.getParameter("taskId"));

                    if (!requestService.hasExistingRequest(taskId, currentUser.getId())) {
                        Request newRequest = new Request();
                        newRequest.setStatus(TypeRequest.EN_ATTENT);
                        newRequest.setTask_id(taskService.getById(taskId));
                        newRequest.setUser_id(currentUser);

                        currentUser.setJeton_Monsuel(currentUser.getJeton_Monsuel() - 1);
                        userService.updateUser(currentUser);

                        requestService.createRequest(newRequest);

                        List<Request> requests = requestService.getALlRequests();
                        request.setAttribute("requests", requests);
                    }
                }
                response.sendRedirect("tasks");
            }
            else if ("update_accepte".equals(action)) {
                try {
                    int requestId = Integer.parseInt(request.getParameter("id"));
                    Request request1 = requestService.getById(requestId);

                    if (request1 != null && request1.getTask_id() != null) {
                        Task task = taskService.getById(request1.getTask_id().getId());
                        request.setAttribute("task", String.valueOf(task.getId()));
                        request.setAttribute("request", String.valueOf(request1.getId()));
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/accepteRequest.jsp");
                        dispatcher.forward(request, response);
                    } else {
                        throw new ServletException("Requête ou tâche invalide");
                    }
                } catch (NumberFormatException e) {
                    throw new ServletException("ID de requête invalide", e);
                }
            }
            else if ("confirm_accept".equals(action)) {
                int taskId = Integer.parseInt(request.getParameter("taskId"));
                int requestId = Integer.parseInt(request.getParameter("requestId"));
                int newUserId = Integer.parseInt(request.getParameter("userId"));

                Request req = requestService.getById(requestId);
                req.setStatus(TypeRequest.ACCEPTE);
                requestService.update(req);

                Task task = taskService.getById(taskId);
                task.setAssignedTo(userService.getUserById(newUserId));
                taskService.updateTask(task);

                response.sendRedirect("requests");
            }
        }

}
