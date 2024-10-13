<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.project.entite.Request" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="fr">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Gestion des requests</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

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

<jsp:include page="layouts/sidebar.jsp" />

<div class="main-content" style="margin-left: 260px;">

<h1 class="header-title">Gestion des requests</h1>

<div class="container">


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
        for (Request requestB : requests) {
      %>
      <tr>
        <td><%= requestB.getId() %></td>
        <td><%= requestB.getUser().getName() %></td>
        <td><%= requestB.getTask().getTitre() %></td>
        <td><%= requestB.getStatus() %></td>

        <%
          if (requestB.getStatus().equals("EN_ATTENT")){


        %>
        <td>

          <form action="requests" method="post" class="d-inline">
            <input type="hidden" name="id" value="<%= requestB.getId() %>"/>
            <button type="submit" name="action" value="update_accepte" class="btn btn-success btn-sm">
              Accepter
            </button>
          </form>


          <form action="requests" method="post" class="d-inline">
            <input type="hidden" name="id" value="<%= requestB.getId() %>"/>
            <button type="submit"  name="action" class="btn btn-danger btn-sm" value="update_refuse">
              Refusé
            </button>
          </form>


        </td>
        <%}else {%>
        <td>l'operation est déja faite</td>
        <%}%>

      </tr>
      <%
        }
      %>
      </tbody>
    </table>
  </div>
</div>
</div>


<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>

