<%@ page import="org.project.entite.User" %>
<style>
  .btn-login {
    margin-left: 50px;
  }

</style>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
  <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<nav class="navbar navbar-expand-lg navbar-light bg-body-tertiary">
  <div class="container">

    <div class="collapse navbar-collapse" id="navbarButtonsExample">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <span class="navbar-brand mb-0 h1">DevSync</span>
        </li>
      </ul>

      <div class="d-flex align-items-center">
        <%
          User user = (User) session.getAttribute("currentUser");
          if (user != null) {
        %>
        <div class="dropdown">
          <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
            <%= user.getName() %>
          </button>
          <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
            <li><a class="dropdown-item" href="logoutUser">Logout</a></li>
            <li><a class="dropdown-item" href="/testmaven02/users">Dashboard</a></li>
          </ul>
        </div>
        <%
        } else {
        %>


        <%
          }
        %>
      </div>

    </div>
  </div>
</nav>