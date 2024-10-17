<%@ page import="org.project.entite.Task" %>
<%@ page import="org.project.entite.User" %>
<%@ page import="org.project.Enum.TypeRole" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Modifier Status de la Tâche</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<jsp:include page="layouts/nav.jsp" />

<h1 class="text-center mb-4">Modifier Status de la Tâche</h1>

<%
    Task task = (Task) request.getAttribute("task");
    if (task != null) {
%>
<form action="tasks" method="post" style="margin: 50px">
    <input type="hidden" name="id" value="<%= task.getId() %>"/>
    <div class="form-group">
        <label for="status">Status</label>
        <select name="status" class="form-control" id="status" required>
            <option value="<%= task.getStatus() %>" selected><%= task.getStatus() %></option>
            <option value="en_attend">En attente</option>
            <option value="en_cours">En cours</option>
            <option value="termine">Complétée</option>
        </select>
    </div>
    <button type="submit" name="action" value="update_status" class="btn btn-primary">
        Enregistrer les modifications
    </button>
</form>
<%
} else {
%>
<p class="text-center">La tâche demandée n'existe pas.</p>
<%
    }
%>

</body>
</html>