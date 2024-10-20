<%@ page import="org.project.entite.User" %>
<%@ page import="org.project.Enum.TypeRole" %>
<div class="sidebar">
  <ul class="nav flex-column">
    <li class="nav-item">
      <a class="nav-link active" href="tasks">Taches</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="tags">Tags</a>
    </li>
    <% User user = (User) session.getAttribute("currentUser");
      if (user.getRole() == TypeRole.MANAGER) {
    %>
    <li class="nav-item">
      <a class="nav-link" href="requests">Requests</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="stats">Statistiques</a>
    </li>
    <%}%>
  </ul>
</div>

<style>
  .sidebar {
    width: 250px;
    position: fixed;
    top: 0;
    left: 0;
    height: 100%;
    background-color: #343a40;
    padding-top: 20px;
    color: #fff;
  }

  .nav-link {
    color: #ffffff;
  }

  .nav-link.active {
    font-weight: bold;
  }
</style>
