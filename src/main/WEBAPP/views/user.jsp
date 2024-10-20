<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.project.entite.User" %>
<%@ page import="java.util.List" %>
<%@ page import="org.project.Enum.TypeRole" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion des Utilisateurs</title>

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

<h1 class="header-title">Gestion des Utilisateurs</h1>

<div class="container">
    <button type="button" class="btn btn-success mb-3" data-toggle="modal" data-target="#addUserModal">
        Ajouter un Utilisateur
    </button>

    <div class="table-container">
        <table class="table table-striped table-bordered">
            <thead class="thead-dark">
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Email</th>
                <th>Mot de pass</th>
                <th>Role</th>
                <th>Jeton Monsuel</th>
                <th>Jeton Annuel</th>
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
                <td>*******</td>
                <td><%= user.getRole() %></td>
                <%if(user.getRole() == TypeRole.valueOf("USER")){%>
                <td><%= user.getJeton_Monsuel() %></td>
                <td><%= user.getJeton_Annuel() %></td>
                <%}else {%>
                <td>c'est un admin</td>
                <td>c'est un admin</td>

                <%}%>

                <td>
<%--                    <form action="users" method="post" class="d-inline">--%>
<%--                        <input type="hidden" name="id" value="<%= user.getId() %>"/>--%>
<%--                        <button type="submit" name="action" value="delete" class="btn btn-danger btn-sm">--%>
<%--                            Supprimer--%>
<%--                        </button>--%>
<%--                    </form>--%>

                    <form action="users" method="get" class="d-inline">
                        <input type="hidden" name="id" value="<%= user.getId() %>"/>
                        <button type="submit" class="btn btn-primary btn-sm">
                            Modifier
                        </button>
                    </form>

                    <form action="loginUser" method="post" class="d-inline">
                        <input type="hidden" name="userId" value="<%= user.getId() %>"/>
                        <button type="submit" class="btn btn-info btn-sm">Login</button>
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

<div class="modal fade" id="addUserModal" tabindex="-1" role="dialog" aria-labelledby="addUserModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addUserModalLabel">Ajouter un Utilisateur</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form action="users" method="post" id="addUserForm">
                    <div class="form-group">
                        <label for="name">Nom</label>
                        <input type="text" name="name" class="form-control" id="name" placeholder="Nom" required>
                    </div>
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" name="email" class="form-control" id="email" placeholder="Email" required>
                    </div>
                    <div class="form-group">
                        <label for="mot_de_pass">Mot de pass</label>
                        <input type="password" name="mot_de_pass" class="form-control" id="mot_de_pass" placeholder="Mot de pass" required>
                    </div>
                    <div class="form-group">
                        <label for="role">Role</label>
                        <select name="role" class="form-control" id="role" required>
                            <option value="MANAGER">Manager</option>
                            <option value="USER">User</option>

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

