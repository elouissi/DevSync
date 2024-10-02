package org.project.controller;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "TestConnectionServlet", value = "/test-connection")
public class TestConnectionServlet extends HttpServlet {
    private String message;

    @Override
    public void init() throws ServletException {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("myPersistenceUnit");
            message = "Connexion réussie à la base de données PostgreSQL !";
        } catch (Exception e) {
            message = "Erreur de connexion à la base de données : " + e.getMessage();
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("connectionMessage", message);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }
}