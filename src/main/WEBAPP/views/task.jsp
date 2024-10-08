<%@ page import="org.project.entite.User" %><%--
  Created by IntelliJ IDEA.
  User: Youcode
  Date: 08/10/2024
  Time: 11:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tasks</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
<jsp:include page="layouts/nav.jsp" />

<h1 class="text-center mb-4">Tâches de <%= ((User) session.getAttribute("currentUser")).getName() %></h1>


</body>
</html>
