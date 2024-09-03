<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.restaurant.model.User" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
//    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute("user") == null) {
        response.sendRedirect("signin.jsp");
        return;
    }
    User user = (User) session.getAttribute("user");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Profile</title>
    <link rel="stylesheet" href="css/profile.css">
</head>
<body>
    <div class="profile-container">
        <h2>Your Profile</h2>
        <form action="ProfileController" method="post" enctype="multipart/form-data">
            <div class="input-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" value="<%= user.getUsername() %>" required>
            </div>
            <div class="input-group">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" value="<%= user.getName() %>" required>
            </div>
            <div class="input-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="<%= user.getEmail() %>" required>
            </div>
            <div class="input-group">
                <label for="contactInfo">Contact Info:</label>
                <input type="text" id="contactInfo" name="contactInfo" value="<%= user.getContactInfo() %>">
            </div>
            <div class="input-group">
                <label for="profileImage">Profile Image:</label>
                <input type="file" id="profileImage" name="profileImage">
                <% if (user.getImagePath()!= null && !user.getImagePath().isEmpty()) { %>
                    <img src="uploads/<%= user.getImagePath()%>" alt="Profile Image" class="profile-image-preview">
                <% } %>
            </div>
            <% if (user.getRoleID() == 1) { %>
            <div class="input-group">
                <label for="roleId">Role:</label>
                <select id="roleId" name="roleId">
                    <option value="">Select Role</option>
                    <option value="1" <%= user.getRoleID() == 1 ? "selected" : "" %>>Administrator</option>
                    <option value="2" <%= user.getRoleID() == 2 ? "selected" : "" %>>Restaurant Staff</option>
                    <option value="3" <%= user.getRoleID() == 3 ? "selected" : "" %>>Customer</option>
                </select>
            </div>
            <% } %>
            <div class="input-group">
                <label for="password">New Password:</label>
                <input type="password" id="password" name="password">
            </div>
            <div class="input-group">
                <button type="submit">Update Profile</button>
            </div>
            <div class="success-message">
                <%= request.getAttribute("successMessage") != null ? request.getAttribute("successMessage") : "" %>
            </div>
            <div class="error-message">
                <%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "" %>
            </div>
        </form>
    </div>
</body>
</html>
