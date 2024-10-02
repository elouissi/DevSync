<%@ page import="java.awt.*" %>
<%@ page import="org.project.entite.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Gestion des Utilisateurs</title>
</head>
<body>
<h1>Liste des Utilisateurs</h1>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Nom</th>
        <th>Email</th>
        <th>Actions</th>
    </tr>
    <%
        List<User> users = (List<User>) request.getAttribute("users");
        for (User user : users) {
    %>
    <tr>
        <td><%= user.getId() %></td>
        <td><%= user.getName() %></td>
        <td><%= user.getEmail() %></td>
        <td>
            <form action="users" method="post" style="display:inline;">
                <input type="hidden" name="id" value="<%= user.getId() %>"/>
                <input type="submit" name="action" value="delete"/>
            </form>
            <form action="users" method="post" style="display:inline;">
                <input type="hidden" name="id" value="<%= user.getId() %>"/>
                <input type="text" name="name" value="<%= user.getName() %>" required/>
                <input type="email" name="email" value="<%= user.getEmail() %>" required/>
                <input type="submit" name="action" value="update"/>
            </form>
        </td>
    </tr>
    <%
        }
    %>
</table>

<h2>Ajouter un Utilisateur</h2>
<form action="users" method="post">
    <input type="text" name="name" placeholder="Nom" required/>
    <input type="email" name="email" placeholder="Email" required/>
    <input type="submit" name="action" value="create"/>
</form>

</body>
</html>
