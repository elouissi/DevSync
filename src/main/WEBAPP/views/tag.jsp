<%@ page import="org.project.entite.Tag" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Gestion des Tags</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<jsp:include page="layouts/nav.jsp" />

<jsp:include page="layouts/sidebar.jsp" />

<div class="main-content" style="margin-left: 260px;">
  <h1>Gestion des Tags</h1>

  <button type="button" class="btn btn-success mb-3" data-toggle="modal" data-target="#addTagModal">
    Ajouter un Tag
  </button>

  <table class="table table-bordered">
    <thead class="thead-dark">
    <tr>
      <th>ID</th>
      <th>Nom du Tag</th>
      <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <%
      List<Tag> tags = (List<Tag>) request.getAttribute("tags");

      for (Tag tag : tags) {
    %>
    <tr>
      <td><%= tag.getId() %></td>
      <td><%= tag.getName() %></td>
      <td>
        <form action="tags" method="post" class="d-inline">
          <input type="hidden" name="id" value="<%= tag.getId() %>"/>
          <button type="submit" name="action" value="delete" class="btn btn-danger btn-sm">
            Supprimer
          </button>
        </form>
        <form action="tags" method="get" class="d-inline">
          <input type="hidden" name="id" value="<%= tag.getId() %>"/>
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

  <div class="modal fade" id="addTagModal" tabindex="-1" role="dialog" aria-labelledby="addTagModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="addTagModalLabel">Ajouter un Tag</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <form action="tags" method="post">
            <div class="form-group">
              <label for="name">Nom du Tag</label>
              <input type="text" name="name" class="form-control" id="name" placeholder="Nom du tag" required>
            </div>
            <button type="submit" name="action" value="create" class="btn btn-success">Ajouter</button>
          </form>
        </div>
      </div>
    </div>
  </div>

</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
