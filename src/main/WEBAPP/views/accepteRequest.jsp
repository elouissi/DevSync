<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.project.entite.Task" %>
<%@ page import="org.project.entite.User" %>
<%@ page import="org.project.service.UserService" %>
<%@ page import="org.project.service.TaskService" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.project.Enum.TypeRole" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Accepter la requête</title>
  <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="layouts/nav.jsp" />
<div class="container mt-5">
  <h1 class="text-center mb-4">Accepter la requête et réassigner la tâche</h1>

  <%
    List<User> users = new ArrayList<>();
    Task task = null;

    try {
      String taskId = (String) request.getAttribute("task");
      String requestId = (String) request.getAttribute("request");

      if (taskId == null || taskId.trim().isEmpty()) {
        throw new IllegalArgumentException("Task ID est manquant");
      }

      UserService userService = new UserService();
      TaskService taskService = new TaskService();

      task = taskService.getById(Integer.parseInt(taskId));

      if (task != null && task.getAssignedTo() != null) {
        Task finalTask = task;
        users = userService.getAllUsers()
                .stream()
                .filter(user -> ((user.getId() != finalTask.getAssignedTo().getId()) && user.getRole() == TypeRole.USER)||user.getId()== finalTask.getCreatedBy().getId() )
                .collect(Collectors.toList());
      }
    } catch (Exception e) {
      out.println("<div class='alert alert-danger'>Erreur: " + e.getMessage() + "</div>");
      return;
    }
  %>

  <form action="requests" method="post">
    <input type="hidden" name="taskId" value="<%= request.getAttribute("task") %>"/>
    <input type="hidden" name="requestId" value="<%= request.getAttribute("request") %>"/>

    <div class="form-group">
      <label for="userId">Sélectionner un utilisateur :</label>
      <select name="userId" id="userId" class="form-control" required>
        <option value="">Choisir un utilisateur</option>
        <% for(User user : users) { %>
        <option value="<%= user.getId() %>"><%= user.getName() %></option>
        <% } %>
      </select>
    </div>

    <div class="text-center">
      <button type="submit" name="action" value="confirm_accept" class="btn btn-primary">
        Confirmer l'acceptation et réassigner
      </button>
      <a href="requests" class="btn btn-secondary ml-2">Annuler</a>
    </div>
  </form>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>