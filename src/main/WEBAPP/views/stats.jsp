<%@ page import="org.project.entite.Task" %>
<%@ page import="java.util.List" %>
<%@ page import="org.project.Enum.TypeStatus" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Statistiques des Tâches</title>
  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
<h1>Vue d'ensemble des tâches assignées</h1>

<form method="get" action="stats">
  <label for="filter">Filtrer par :</label>
  <select name="filter" id="filter" onchange="this.form.submit()">
    <option value="semaine" <%= "semaine".equals(request.getParameter("filter")) ? "selected" : "" %>>Cette semaine</option>
    <option value="mois" <%= "mois".equals(request.getParameter("filter")) ? "selected" : "" %>>Ce mois</option>
    <option value="année" <%= "année".equals(request.getParameter("filter")) ? "selected" : "" %>>Cette année</option>
  </select>
</form>

<h2>Tâches assignées</h2>
<table border="1">
  <thead>
  <tr>
    <th>Titre</th>
    <th>Description</th>
    <th>Statut</th>
    <th>Date de début</th>
    <th>Date de fin</th>
  </tr>
  </thead>
  <tbody>
  <%
    List<Task> tasks = (List<Task>) request.getAttribute("tasks");
    if (tasks != null && !tasks.isEmpty()) {
      for (Task task : tasks) {
  %>
  <tr>
    <td><%= task.getTitre() %></td>
    <td><%= task.getDescription() %></td>
    <td><%= task.getStatus() %></td>
    <td><%= task.getDateDebut() != null ? task.getDateDebut() : "N/A" %></td>
    <td><%= task.getDateFin() != null ? task.getDateFin() : "N/A" %></td>
  </tr>
  <%
    }
  } else {
  %>
  <tr>
    <td colspan="5">Aucune tâche trouvée pour cette période.</td>
  </tr>
  <% } %>
  </tbody>
</table>

<h2>Statistiques d'achèvement</h2>
<canvas id="taskCompletionChart" width="400" height="200"></canvas>

<script>
  const ctx = document.getElementById('taskCompletionChart').getContext('2d');

  const tasks = <%= tasks != null ? tasks.size() : 0 %>;
  const completedTasks = <%= tasks != null ? tasks.stream().filter(t -> t.getStatus() == TypeStatus.termine).count() : 0 %>;

  const data = {
    labels: ['Tâches complétées', 'Tâches en cours'],
    datasets: [{
      label: 'Pourcentage d\'achèvement',
      data: [completedTasks, tasks - completedTasks],
      backgroundColor: ['rgba(75, 192, 192, 0.2)', 'rgba(255, 99, 132, 0.2)'],
      borderColor: ['rgba(75, 192, 192, 1)', 'rgba(255, 99, 132, 1)'],
      borderWidth: 1
    }]
  };

  // Configuration du graphique
  const config = {
    type: 'doughnut',
    data: data,
    options: {
      responsive: true,
      plugins: {
        legend: {
          position: 'top',
        },
        tooltip: {
          callbacks: {
            label: function(tooltipItem) {
              return tooltipItem.label + ': ' + tooltipItem.raw + ' tâches';
            }
          }
        }
      }
    }
  };

  // Créer le graphique
  const taskCompletionChart = new Chart(ctx, config);
</script>
</body>
</html>
