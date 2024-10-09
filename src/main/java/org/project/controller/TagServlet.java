package org.project.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.project.Enum.TypeStatus;
import org.project.entite.Tag;
import org.project.entite.Task;
import org.project.service.TagService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "TagServlet", value = "/tags")

public class TagServlet extends HttpServlet {
    private TagService tagService;

    @Override
    public void init() throws ServletException {
        tagService = new TagService();
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect("users");
            return;
        }
        List<Tag> tags = tagService.getALlTags();
        request.setAttribute("tags", tags);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/tag.jsp");
        dispatcher.forward(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        if ("create".equals(action)) {
            String name = request.getParameter("name");
            Tag tag = new Tag();
            tag.setName(name);
            tagService.createTag(tag);

        }
        response.sendRedirect("tags");
    }
}
