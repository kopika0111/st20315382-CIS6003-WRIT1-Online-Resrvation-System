<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="com.restaurant.model.Menu"%>
<!DOCTYPE html>
<html lang="en">
<%
    Menu menu = (Menu) request.getAttribute("menu");
    String title = menu != null ? "Edit Menu" : "Add Menu";
    String action = menu != null ? "updateMenu" : "addMenu";
%>
<head>
    <meta charset="UTF-8">
    <title><%= title %></title>
    <link rel="stylesheet" href="css/MenuForm.css">
</head>
<body>
    <div class="container">
        <header>
            <h1><%= title %></h1>
            <a href="MenuController?action=list" class="back-btn">Back to Menu List</a>
        </header>
        <section class="form-section">
            <form action="MenuController" method="post" enctype="multipart/form-data">
                <input type="hidden" name="action" value="<%= action %>">
                <% if(menu != null) { %>
                    <input type="hidden" name="menuID" value="<%= menu != null ?  menu.getMenuID() : "" %>">
                <% } %>

                <label for="name">Name:</label>
                <input type="text" id="name" name="name" value="<%= menu != null ? menu.getName() : "" %>" required>

                <label for="category">Category:</label>
                <input type="text" id="category" name="category" value="<%= menu != null ? menu.getCategory() : "" %>" required>

                <label for="description">Description:</label>
                <textarea id="description" name="description" required><%= menu != null ? menu.getDescription() : "" %></textarea>

                <label for="price">Price:</label>
                <input type="number" id="price" name="price" step="0.01" value="<%= menu != null ? menu.getPrice() : "" %>" required>

                <label for="image">Image:</label>
                <input type="file" id="image" name="image">
                <% if (menu != null && menu.getImagePath()!= null && !menu.getImagePath().isEmpty()) { %>
                    <img src="uploads/<%= menu.getImagePath()%>" alt="Item Image" class="profile-image-preview">
                    <% } %> <br>
                <button type="submit" class="submit-btn"><%= title %></button>
            </form>
        </section>
    </div>
</body>
</html>
