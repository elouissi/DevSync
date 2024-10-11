<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.project.entite.Request" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="fr">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Gestion des requests</title>

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

<h1 class="header-title">Gestion des requests</h1>

<div class="container">
  <button type="button" class="btn btn-success mb-3" data-toggle="modal" data-target="#addRequestModal">
    Ajouter un Utilisateur
  </button>

  <div class="table-container">
    <table class="table table-striped table-bordered">
      <thead class="thead-dark">
      <tr>
        <th>ID</th>
        <th>Nom d'utilisateur </th>
        <th>titre du task</th>
        <th>Status</th>
        <th>Actions</th>
      </tr>
      </thead>
      <tbody>
      <%
        List<Request> requests = (List<Request>) request.getAttribute("requests");
        for (Request user : requests) {
      %>
      <tr>
        <td><%= user.getId() %></td>
        <td><%= user.getName() %></td>


        <td>
          <form action="requests" method="post" class="d-inline">
            <input type="hidden" name="id" value="<%= user.getId() %>"/>
            <button type="submit" name="action" value="delete" class="btn btn-danger btn-sm">
              Supprimer
            </button>
          </form>

          <form action="requests" method="get" class="d-inline">
            <input type="hidden" name="id" value="<%= user.getId() %>"/>
            <button type="submit" class="btn btn-primary btn-sm">
              Modifier
            </button>
          </form>

          <form action="loginRequest" method="post" class="d-inline">
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

<div class="modal fade" id="addRequestModal" tabindex="-1" role="dialog" aria-labelledby="addRequestModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="addRequestModalLabel">Ajouter un Utilisateur</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form action="requests" method="post" id="addRequestForm">
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
            <input type="text" name="role" class="form-control" id="role" placeholder="Role" required>
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

