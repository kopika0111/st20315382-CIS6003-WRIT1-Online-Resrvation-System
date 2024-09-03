<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.restaurant.model.Table"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= request.getAttribute("table") != null ? "Edit Table" : "Add Table" %></title>
    <link rel="stylesheet" href="css/tableForm.css">
</head>
<body>
    <div class="container">
        <h1><%= request.getAttribute("table") != null ? "Edit Table" : "Add Table" %></h1>
        <form action="TableController" method="post">
            <input type="hidden" name="action" value="<%= request.getAttribute("table") != null ? "update" : "insert" %>">
            <input type="hidden" name="tableID" value="<%= request.getAttribute("table") != null ? ((Table) request.getAttribute("table")).getTableID() : "" %>">

            <label for="capacity">Capacity:</label>
            <input type="number" id="capacity" name="capacity" value="<%= request.getAttribute("table") != null ? ((Table) request.getAttribute("table")).getCapacity() : "" %>" required>
            
            <label for="status">Status</label>
            <input type="text" id="status" name="status" value="<%= request.getAttribute("table") != null ? ((Table) request.getAttribute("table")).getStatus() : "" %>" required>

            <button type="submit"><%= request.getAttribute("table") != null ? "Update Table" : "Add Table" %></button>
        </form>
    </div>
</body>
</html>
