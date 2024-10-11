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
import org.project.entite.User;
import org.project.service.RequestService;
import org.project.service.TaskService;

import java.io.IOException;
import java.util.List;
@WebServlet(name = "RequestServlet", value = "/requests")

public class RequestServlet extends HttpServlet {
    private RequestService requestService;
    private TaskService taskService;

    @Override
    public void init() throws ServletException {
        requestService = new RequestService();
        taskService = new TaskService();
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

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String action = request.getParameter("action");
        if ("rejectCreate".equals(action)) {
            System.out.println("la requet a une direction correct2");
            int taskId = Integer.parseInt(request.getParameter("taskId"));

            Request newRequest = new Request();
            newRequest.setStatus(TypeRequest.EN_ATTENT);
            newRequest.setTask_id(taskService.getById(taskId));
            User currentUser = (User) request.getSession().getAttribute("currentUser");
            newRequest.setUser_id(currentUser);

            System.out.println("task id= " + taskId +"type request = " + newRequest.getStatus() + "task = " +newRequest.getTask_id());
            requestService.createRequest(newRequest);
            System.out.println(newRequest);
            if ("update".equals(action)) {

            }
            response.sendRedirect("tasks");
        }
    }
}
