<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Hello World index.JSP</title>
</head>
<body>
<h1>Hello, j!</h1>

<p>
    <%= request.getAttribute("connectionMessage") %>
</p>
</body>
</html>
