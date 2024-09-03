<%@ page contentType="text/html;charset=UTF-8" %>
<%@page import="com.restaurant.model.User"%>
<!DOCTYPE html>
<html lang="en">
    <% 
        User user = (User) request.getAttribute("user");
        String title = user != null ? "Edit User" : "Add User"; 
        String action = user != null ? "updateUser" : "addUser"; 
        System.out.println("user form test : " + title);
    %>
<head>
    <meta charset="UTF-8">
    <title><%= title %></title>
    <link rel="stylesheet" href="css/userForm.css">
</head>
<body>
    <div class="container">
        <header>
            <h1><%= title %></h1>
            <a href="manageUsers.jsp" class="back-btn">Back to Manage Users</a>
        </header>
        <section class="form-section">
            <form action="UserController" method="post" enctype="multipart/form-data">
                <input type="hidden" name="action" value="<%= action %>">
                <input type="hidden" name="userID" value="<%= user != null ? user.getUserID() : "" %>">

                <label for="username">Username:</label>
                <input type="text" id="username" name="username" value="<%= user != null ? user.getUsername() : "" %>" required>

                <label for="name">Name:</label>
                <input type="text" id="name" name="name" value="<%= user != null ? user.getName()  : ""%>" required>

                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="<%= user != null ? user.getEmail() : ""%>" required>

                <label for="password">Password:</label>
                <input type="password" id="password" name="password" value="<%= user != null ? user.getPassword()  : ""%>" required>

                <label for="contactInfo">Contact Info:</label>
                <input type="text" id="contactInfo" name="contactInfo" value="<%= user != null ? user.getContactInfo()  : ""%>">

                <label for="roleID">Role:</label>
                <select id="roleID" name="roleID" <%= user != null && (user.getRoleID() == 1) ? "disabled" : "" %>>
                    <option value="1" <%= user != null && (user.getRoleID() == 1) ? "selected" : "" %>>Administrator</option>
                    <option value="2" <%= user != null && (user.getRoleID() == 2) ? "selected" : "" %>>Staff</option>
                    <option value="3" <%= user != null && (user.getRoleID() == 3) ? "selected" : "" %>>Customer</option>
                </select>

                <label for="image">Profile Image:</label>
                <input type="file" id="image" name="image">
                <% if (user != null && user.getImagePath()!= null && !user.getImagePath().isEmpty()) { %>
                    <img src="uploads/<%= user.getImagePath()%>" alt="Profile Image" class="profile-image-preview">
                <% } %>
                <br>
                <button type="submit" class="submit-btn">
                    <%= title %>
                </button>
            </form>
        </section>
    </div>
</body>
</html>
