<%@ page import="org.project.entite.Task" %>
<%@ page import="java.util.List" %>
<%@ page import="org.project.Enum.TypeStatus" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.project.entite.Tag" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Statistiques des Tâches</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/css/bootstrap.min.css">
</head>
<body>
<jsp:include page="layouts/nav.jsp" />
<jsp:include page="layouts/sidebar.jsp" />
<div class="main-content" style="margin-left: 260px;">
  <form method="get" action="stats">
    <div class="form-group">
      <%--@declare id="tags"--%><label for="tags">Sélectionner un ou plusieurs tags</label>
      <select id="tags-select" name="tags" class="form-control">
        <option value="">Choisissez un tag</option>
        <%
          List<Tag> tagsList = (List<Tag>) request.getAttribute("allTags");
          for (Tag tag : tagsList) {
        %>
        <option value="<%= tag.getId() %>"><%= tag.getName() %></option>
        <% } %>
      </select>
    </div>
    <!-- Date range filter -->
    <div class="form-group">
      <label for="start-date">Date de début :</label>
      <input type="date" id="start-date" name="start_date" class="form-control">
    </div>
    <div class="form-group">
      <label for="end-date">Date de fin :</label>
      <input type="date" id="end-date" name="end_date" class="form-control">
    </div>

    <div class="form-group">
      <label>Tags sélectionnés :</label>
      <ul id="selected-tags" class="list-group"></ul>
    </div>

    <input type="hidden" name="selected_tags" id="selected-tags-input" />

    <button type="submit" class="btn btn-success">Chercher</button>
  </form>
  <table class="table table-bordered">
    <thead>
    <tr>
      <th>Titre de la Tâche</th>
      <th>Description</th>
      <th>Status</th>
      <th>tags</th>
      <th>Nombre de Requêtes</th>
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
      <td>
        <%
          List<Tag> tags = task.getTags();
          if (tags != null && !tags.isEmpty()) {
            for (Tag tag : tags) {
        %>
        <span class="badge badge-info"><%= tag.getName() %></span>
        <%
          }
        } else {
        %>
        <span>Aucun tag</span>
        <%
          }
        %>
      </td>
      <td><%= task.getRequests().size() %></td>
    </tr>
    <%
      }
    } else {
    %>
    <tr>
      <td colspan="5">Aucune tâche trouvée</td>
    </tr>
    <%
      }
    %>
    </tbody>
  </table>
<div class="container mt-5">
  <h2>Statistiques des tâches</h2>

  <div class="progress mb-3">
    <div class="progress-bar bg-success" role="progressbar"
         style="width: <%= request.getAttribute("percentageEn_cours") %>%"
         aria-valuenow="<%= request.getAttribute("percentageEn_cours") %>"
         aria-valuemin="0" aria-valuemax="100">
      En cours (<%= String.format("%.2f", request.getAttribute("percentageEn_cours")) %>%)
    </div>
  </div>

  <div class="progress mb-3">
    <div class="progress-bar bg-warning" role="progressbar"
         style="width: <%= request.getAttribute("percentageEn_attend") %>%"
         aria-valuenow="<%= request.getAttribute("percentageEn_attend") %>"
         aria-valuemin="0" aria-valuemax="100">
      En attente (<%= String.format("%.2f", request.getAttribute("percentageEn_attend")) %>%)
    </div>
  </div>

  <div class="progress mb-3">
    <div class="progress-bar bg-info" role="progressbar"
         style="width: <%= request.getAttribute("percentageTermine") %>%"
         aria-valuenow="<%= request.getAttribute("percentageTermine") %>"
         aria-valuemin="0" aria-valuemax="100">
      Terminée (<%= String.format("%.2f", request.getAttribute("percentageTermine")) %>%)
    </div>
  </div>

  <div class="progress mb-3">
    <div class="progress-bar bg-danger" role="progressbar"
         style="width: <%= request.getAttribute("percentageInComplete") %>%"
         aria-valuenow="<%= request.getAttribute("percentageInComplete") %>"
         aria-valuemin="0" aria-valuemax="100">
      Incomplète (<%= String.format("%.2f", request.getAttribute("percentageInComplete")) %>%)
    </div>
  </div>

</div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
<script>
  document.addEventListener('DOMContentLoaded', function() {
    const tagsSelect = document.getElementById('tags-select');
    const selectedTagsList = document.getElementById('selected-tags');
    const selectedTagsInput = document.getElementById('selected-tags-input');

    // Tableau pour stocker les tags sélectionnés
    let selectedTags = [];

    // Ajouter un tag lorsqu'un tag est sélectionné
    tagsSelect.addEventListener('change', function() {
      const selectedOption = tagsSelect.options[tagsSelect.selectedIndex];
      const tagId = selectedOption.value;
      const tagName = selectedOption.text;

      // Vérifier si le tag n'a pas déjà été ajouté
      if (tagId && !selectedTags.includes(tagId)) {
        // Ajouter le tag à la liste des tags sélectionnés
        selectedTags.push(tagId);

        // Mettre à jour l'affichage des tags sélectionnés
        const listItem = document.createElement('li');
        listItem.className = 'list-group-item d-flex justify-content-between align-items-center';
        listItem.textContent = tagName;

        // Ajouter un bouton pour supprimer le tag
        const removeButton = document.createElement('button');
        removeButton.textContent = 'X';
        removeButton.className = 'btn btn-danger btn-sm';
        removeButton.onclick = function() {
          // Supprimer le tag de l'interface et du tableau
          selectedTagsList.removeChild(listItem);
          selectedTags = selectedTags.filter(id => id !== tagId);
          updateSelectedTagsInput();
        };

        listItem.appendChild(removeButton);
        selectedTagsList.appendChild(listItem);

        // Mettre à jour le champ caché avec les tags sélectionnés
        updateSelectedTagsInput();
      }

      tagsSelect.selectedIndex = 0;
    });

    // Met à jour la valeur de l'input caché avec les IDs des tags sélectionnés
    function updateSelectedTagsInput() {
      selectedTagsInput.value = selectedTags.join(',');
    }
  });

</script>
</body>
</html>
