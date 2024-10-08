package org.project.controller;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet( name = "logoutUser",value = "/logoutUser")

public class LogoutServlet extends HttpServlet {
     public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
         HttpSession session = request.getSession(false);
         if (session != null){
             session.invalidate();
         }
         response.sendRedirect("users");

     }
}
