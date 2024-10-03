<%@ page import="org.project.entite.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Gestion des Utilisateurs</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-5">

<h1 class="text-center mb-4">Gestion des Utilisateurs</h1>

<table class="table table-striped">
    <thead class="thead-dark">
    <tr>
        <th>ID</th>
        <th>Nom</th>
        <th>Email</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <%
        List<User> users = (List<User>) request.getAttribute("users");
        for (User user : users) {
    %>
    <tr>
        <td><%= user.getId() %></td>
        <td><%= user.getName() %></td>
        <td><%= user.getEmail() %></td>
        <td>
            <!-- Bouton de suppression -->
            <form action="users" method="post" class="d-inline">
                <input type="hidden" name="id" value="<%= user.getId() %>"/>
                <button type="submit" name="action" value="delete" class="btn btn-danger btn-sm">
                    Supprimer
                </button>
            </form>

            <!-- Formulaire de mise à jour -->
<%--            <form action="users" method="post" class="d-inline">--%>
<%--                <input type="hidden" name="id" value="<%= user.getId() %>"/>--%>
<%--                <input type="text" name="name" value="<%= user.getName() %>" class="form-control form-control-sm d-inline-block w-25" required/>--%>
<%--                <input type="email" name="email" value="<%= user.getEmail() %>" class="form-control form-control-sm d-inline-block w-25" required/>--%>
<%--                <button type="submit" name="action" value="update" class="btn btn-primary btn-sm">--%>
<%--                    Modifier--%>
<%--                </button>--%>
<%--            </form>--%>
            <form action="users" method="get" class="d-inline">
                <input type="hidden" name="id" value="<%= user.getId() %>"/>
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

<h2>Ajouter un Utilisateur</h2>
<form action="users" method="post" class="form-inline">
    <div class="form-group mb-2">
        <%--@declare id="name"--%><label for="name" class="sr-only">Nom</label>
        <input type="text" name="name" class="form-control" placeholder="Nom" required/>
    </div>
    <div class="form-group mx-sm-3 mb-2">
        <%--@declare id="email"--%><label for="email" class="sr-only">Email</label>
        <input type="email" name="email" class="form-control" placeholder="Email" required/>
    </div>
    <button type="submit" name="action" value="create" class="btn btn-success mb-2">Ajouter</button>
</form>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
