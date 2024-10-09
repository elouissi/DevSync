<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.project.entite.Task" %>
<%@ page import="java.util.List" %>
<%@ page import="org.project.entite.User" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="org.project.Enum.TypeRole" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion des Tâches</title>

    <style>
        body {
            background-color: #f8f9fa;
        }
        .header-title {
            margin-top: 20px;
            margin-bottom: 30px;
            text-align: center;
        }
        .table-container {
            margin-top: 20px;
        }
    </style>
</head>
<body>
<jsp:include page="layouts/nav.jsp" />

<h1 class="header-title">Gestion des Tâches</h1>

<div class="container">
    <div class="container">
        <%
            User user = (User) session.getAttribute("currentUser");
            if (user != null) {
                if (user.getRole() == TypeRole.MANAGER) {
        %>
        <button type="button" class="btn btn-success mb-3" data-toggle="modal" data-target="#addTaskModal">
            Ajouter une Tâche (MANAGER)
        </button>


        <%
        } else {
        %>
        <button type="button" class="btn btn-warning mb-3" data-toggle="modal" data-target="#addTaskModal">
            Ajouter une Tâche USER
        </button>
        <%
            }
        } else {
        %>
        <p class="text-danger">Vous devez vous connecter pour ajouter une tâche.</p>
        <%
            }
        %>
    </div>

    <div class="table-container">
        <table class="table table-striped table-bordered">
            <thead class="thead-dark">
            <tr>
                <th>ID</th>
                <th>Titre</th>
                <th>Description</th>
                <th>Status</th>
                <th>Date Début</th>
                <th>Date fin</th>
                <th>Créée par</th>
                <th>Assignée à</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<Task> tasks = (List<Task>) request.getAttribute("tasks");
                List<Task> taskF=  tasks.stream().filter((t -> t.getCreatedBy().getId() == user.getId() || t.getAssignedTo().getId() == user.getId())).collect(Collectors.toList());

                for (Task task : taskF) {
            %>
            <tr>
                <td><%= task.getId() %></td>
                <td><%= task.getTitre() %></td>
                <td><%= task.getDescription() %></td>
                <td><%= task.getStatus() %></td>
                <td><%= task.getDateDebut() %></td>
                <td><%= task.getDateFin() %></td>
                <td><%= task.getCreatedBy().getName() %></td>
                <td><%= (task.getAssignedTo() != null) ? task.getAssignedTo().getName() : "Non assignée" %></td>
                <td>
                    <form action="tasks" method="post" class="d-inline">
                        <input type="hidden" name="id" value="<%= task.getId() %>"/>
                        <button type="submit" name="action" value="delete" class="btn btn-danger btn-sm">
                            Supprimer
                        </button>
                    </form>
                    <form action="tasks" method="get" class="d-inline">
                        <input type="hidden" name="id" value="<%= task.getId() %>"/>
                        <button type="submit" class="btn btn-primary btn-sm">
                            Modifier
                        </button>
                    </form>
                </td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
</div>

<div class="modal fade" id="addTaskModal" tabindex="-1" role="dialog" aria-labelledby="addTaskModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addTaskModalLabel">Ajouter une Tâche</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="tasks" method="post" id="addTaskForm">
                    <div class="form-group">
                        <label for="titre">Titre</label>
                        <input type="text" name="titre" class="form-control" id="titre" placeholder="Titre de la tâche" required>
                    </div>
                    <div class="form-group">
                        <label for="description">Description</label>
                        <input type="text" name="description" class="form-control" id="description" placeholder="Description" required>
                    </div>
                    <div class="form-group">
                        <label for="status">Status</label>
                        <select name="status" class="form-control" id="status" required>
                            <option value="en_attend">En attente</option>
                            <option value="en_cours">En cours</option>
                            <option value="termine">Complétée</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="date_debut">Date de début</label>
                        <input type="date" name="date_debut" class="form-control" id="date_debut" required>
                    </div>
                    <div class="form-group">
                        <label for="date_fin">Date de fin</label>
                        <input type="date" name="date_fin" class="form-control" id="date_fin" required>
                    </div>
                    <div class="form-group">
                        <input type="text" name="created_user_id" value="<%= user.getId() %>" style="display: none;">
                        <label for="assigned_user">Assignée à</label>
                        <select name="assigned_user_id" class="form-control" id="assigned_user">
                            <option value="">Non assignée</option>
                            <%
                                List<User> users = (List<User>) request.getAttribute("users");
                                for (User user2 : users) {
                            %>
                            <option value="<%= user2.getId() %>"><%= user2.getName() %></option>
                            <%
                                }
                            %>
                        </select>
                    </div>
                    <button type="submit" name="action" value="create" class="btn btn-success">Ajouter</button>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
