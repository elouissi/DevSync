<%@ page import="org.project.entite.Task" %>
<%@ page import="org.project.service.TaskService" %>
<%@ page import="org.project.entite.User" %>
<%@ page import="org.project.Enum.TypeRole" %>
<%@ page import="java.util.List" %>
<%@ page import="org.project.entite.Tag" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Modifier Tache</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body >
<jsp:include page="layouts/nav.jsp" />
<%
    User user = (User) session.getAttribute("currentUser");

%>


<h1 class="text-center mb-4">Modifier Tache</h1>

<%
    String taskId = request.getParameter("id");
    Task task = new TaskService().getById(Integer.parseInt(taskId));
%>

<form action="users" method="post" style="margin: 50px" >
    <input type="hidden" name="id" value="<%= task.getId() %>"/>

    <div class="form-group">
        <label for="titre">Titre</label>
        <input type="text" value="<%= task.getTitre() %>" name="titre" class="form-control" id="titre" placeholder="Titre de la tâche" required>
    </div>
    <div class="form-group">
        <label for="description">Description</label>
        <input type="text" value="<%= task.getDescription() %>" name="description" class="form-control" id="description" placeholder="Description" required>
    </div>
    <div class="form-group">
        <label for="status">Status</label>
        <select name="status" class="form-control" id="status" required>
            <option value="<%= task.getStatus() %>" selected > <%= task.getStatus() %> </option>
            <option value="en_attend">En attente</option>
            <option value="en_cours">En cours</option>
            <option value="termine">Complétée</option>
        </select>
    </div>
    <div class="form-group">
        <label for="date_debut">Date de début</label>
        <input type="date"value="<%= task.getDateDebut() %>"  name="date_debut" class="form-control" id="date_debut" required>
    </div>
    <div class="form-group">
        <label for="date_fin">Date de fin</label>
        <input type="date" value="<%= task.getDateFin() %>"  name="date_fin" class="form-control" id="date_fin" required>
    </div>
    <div class="form-group">
        <input type="text" name="created_user_id" value="<%= user.getId() %>" style="display: none;">
        <% if (user.getRole() == TypeRole.valueOf("MANAGER")) { %>
        <label for="assigned_user">Assignée à</label>
        <select name="assigned_user_id" class="form-control" id="assigned_user">
            <option value="">Non assignée</option>
            <%
                List<User> users = (List<User>) request.getAttribute("users");
                for (User user2 : users) {
                    boolean isAssigned = task.getAssignedTo() != null && task.getAssignedTo().getId() == user2.getId();
            %>
            <option value="<%= user2.getId() %>" <%= isAssigned ? "selected" : "" %>>
                <%= user2.getName() %>
            </option>
            <%
                }
            %>
        </select>
        <% } else { %>
        <label for="assigned_user">Assignée à</label>
        <select name="assigned_user_id" class="form-control" id="assigned_user">
            <option selected value="<%= user.getId() %>">Assignée à vous-même</option>
        </select>
        <% } %>

    </div>
    <div class="form-group">
        <label for="tags">Choisir les tags</label>
        <select name="tags" id="tags" multiple>
            <%
                List<Tag> allTags = (List<Tag>) request.getAttribute("tags");
                List<Tag> selectedTags = (List<Tag>) request.getAttribute("selectedTags");

                if (allTags != null && !allTags.isEmpty()) {
                    for (Tag tag : allTags) {
                        boolean isSelected = false;
                        if (selectedTags != null) {
                            for (Tag selectedTag : selectedTags) {
                                if (selectedTag.getId() == tag.getId()) {
                                    isSelected = true;
                                    break;
                                }
                            }
                        }
            %>
            <option value="<%= tag.getId() %>" <%= isSelected ? "selected" : "" %>>
                <%= tag.getName() %>
            </option>
            <%
                }
            } else {
            %>
            <option disabled>Aucun tag disponible</option>
            <%
                }
            %>
        </select>
    </div>

    <input type="hidden" id="selected-tags-input" name="selected_tags" value="<%
    if(selectedTags != null && !selectedTags.isEmpty()) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < selectedTags.size(); i++) {
            if(i > 0) sb.append(",");
            sb.append(selectedTags.get(i).getId());
        }
        out.print(sb.toString());
    }
%>" />

    <button type="submit" name="action" value="update" class="btn btn-primary">
        Enregistrer les modifications
    </button>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/habibmhamadi/multi-select-tag@2.0.1/dist/css/multi-select-tag.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/gh/habibmhamadi/multi-select-tag@2.0.1/dist/js/multi-select-tag.js"></script>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Initialisation du MultiSelectTag
            let select = new MultiSelectTag('tags', {
                rounded: true,
                shadow: true,
                placeholder: 'Rechercher...',
                onChange: function(values) {
                    document.getElementById('selected-tags-input').value = values.join(',');
                }
            });

            // Force la mise à jour initiale des tags sélectionnés
            const selectedOptions = document.querySelectorAll('#tags option[selected]');
            if (selectedOptions.length > 0) {
                const selectedValues = Array.from(selectedOptions).map(option => option.value);
                document.getElementById('selected-tags-input').value = selectedValues.join(',');
            }

            // Validation du formulaire
            document.querySelector('form').addEventListener('submit', function(e) {
                const selectedTags = document.getElementById('tags').selectedOptions;
                if (selectedTags.length === 0) {
                    e.preventDefault();
                    alert('Veuillez sélectionner au moins un tag');
                    return false;
                }
            });
        });
    </script>

</body>
</html>
