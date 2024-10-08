<%@ page import="org.project.entite.User" %>
<%@ page import="org.project.service.UserService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Modifier l'Utilisateur</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body >
<jsp:include page="layouts/nav.jsp" />


<h1 class="text-center mb-4">Modifier l'Utilisateur</h1>

<%
    String userId = request.getParameter("id");
    User user = new UserService().getUserById(Integer.parseInt(userId));
%>

<form action="users" method="post">
    <input type="hidden" name="id" value="<%= user.getId() %>"/>

    <div class="form-group">
        <%--@declare id="name"--%><label for="name">Nom</label>
        <input type="text" name="name" class="form-control" value="<%= user.getName() %>" required/>
    </div>

    <div class="form-group">
        <%--@declare id="email"--%><label for="email">Email</label>
        <input type="email" name="email" class="form-control" value="<%= user.getEmail() %>" required/>
    </div>
    <div class="form-group">
        <%--@declare id="mot_de_pass"--%><label for="mot_de_pass">mot de pass</label>
        <input type="text" name="mot_de_pass" class="form-control" value="<%= user.getMot_de_pass() %>" required/>
    </div>
    <div class="form-group">
        <%--@declare id="role"--%><label for="name">Role</label>
        <input type="text" name="role" class="form-control" value="<%= user.getRole() %>" required/>
    </div>

    <button type="submit" name="action" value="update" class="btn btn-primary">Enregistrer les modifications</button>
</form>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
