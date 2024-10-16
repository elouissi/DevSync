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
import org.project.scheduler.RequestScheduler;
import org.project.service.RequestService;
import org.project.service.TaskService;
import org.project.service.UserService;

import java.io.IOException;
import java.time.LocalDate;
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
        RequestScheduler requestScheduler = new RequestScheduler();
        requestScheduler.startScheduler();
        List<Request> requests = requestService.getALlRequests();
        request.setAttribute("requests", requests);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/request.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("rejectCreate".equals(action)) {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("currentUser") == null) {
                response.sendRedirect("users");
                return;
            }

            User currentUser = (User) session.getAttribute("currentUser");
            int taskId = Integer.parseInt(request.getParameter("taskId"));
            Task task = taskService.getById(taskId);
            if (task == null) {
                throw new ServletException("Task not found");
            }

            try {
                if (!requestService.hasExistingRequest(taskId, currentUser.getId())) {

                    // Case 1: Task already requested
                if (task.isRequested()) {
                    // Check if the user has an existing request for this task
                        Request newRequest = new Request();
                        newRequest.setStatus(TypeRequest.EN_ATTENT);
                        newRequest.setTask(task);
                        newRequest.setUser(currentUser);
                        newRequest.setCreated_at(LocalDate.now());

                        // Save the new request
                        requestService.createRequest(newRequest);
                        userService.updateUser(currentUser);

                        session.setAttribute("message", "Request created successfully");

                }
                // Case 2: Task not yet requested, check user's tokens
                else {
                    if (currentUser.getJeton_Monsuel() != 0) {
                        Request newRequest = new Request();
                        newRequest.setStatus(TypeRequest.EN_ATTENT);
                        newRequest.setTask(task);
                        newRequest.setUser(currentUser);
                        newRequest.setCreated_at(LocalDate.now());

                        // Create new request and deduct one token
                        requestService.createRequest(newRequest);
                        currentUser.setJeton_Monsuel(currentUser.getJeton_Monsuel() - 1);
                        userService.updateUser(currentUser);

                        session.setAttribute("message", "Request created successfully. One token deducted.");
                    } else {
                        // User has no tokens left
                        session.setAttribute("error", "Unable to create request. You don't have enough tokens.");
                    }
                }
                } else {
                    session.setAttribute("error", "Unable to create request. You already have an existing request.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("error", "Error creating request: " + e.getMessage());
                response.sendRedirect("tasks");
                return;
            }

            response.sendRedirect("tasks");
        }

            else if ("update_accepte".equals(action)) {
                try {
                    int requestId = Integer.parseInt(request.getParameter("id"));
                    Request request1 = requestService.getById(requestId);

                    if (request1 != null && request1.getTask() != null) {
                        Task task = taskService.getById(request1.getTask().getId());
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
                task.setRequested(true);
                taskService.updateTask(task);

                response.sendRedirect("requests");
            } else if ("update_refuse".equals(action)) {
            int requestId = Integer.parseInt(request.getParameter("id"));
            Request request1 = requestService.getById(requestId);
            if (request1 != null && request1.getTask() != null) {
                request1.setStatus(TypeRequest.REFUSE);
                requestService.update(request1);

            } else {
                throw new ServletException("Requête ou tâche invalide");
            }
            response.sendRedirect("requests");




        }
    }

}
