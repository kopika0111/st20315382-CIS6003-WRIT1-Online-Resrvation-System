<%@page import="java.util.List"%>
<%@page import="com.restaurant.model.User"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manage Users</title>
    <link rel="stylesheet" href="css/manageUsers.css">
</head>
        <script>
        function searchUsers() {
            var input, filter, table, tr, td, i, j, txtValue;
            input = document.getElementById("searchInput");
            filter = input.value.toLowerCase();
            table = document.getElementById("userTable");
            tr = table.getElementsByTagName("tr");

            for (i = 1; i < tr.length; i++) { // Start from 1 to skip the header row
                tr[i].style.display = "none"; // Hide the row by default
                td = tr[i].getElementsByTagName("td");
                for (j = 0; j < td.length; j++) {
                    if (td[j]) {
                        txtValue = td[j].textContent || td[j].innerText;
                        if (txtValue.toLowerCase().indexOf(filter) > -1) {
                            tr[i].style.display = ""; // Show the row if a match is found
                            break;
                        }
                    }
                }
            }
        }
    </script>
<body>
    <div class="container">
        <header>
            <h1>Manage Users</h1>
            <a href="dashboard.jsp" class="back-btn">Back to Dashboard</a>
        </header>
        <!-- Search box -->
        <input type="text" id="searchInput" onkeyup="searchUsers()" placeholder="Search for users..." class="search-box">
        <section class="user-list">
            <table id="userTable">
                <thead>
                    <tr>
                        <th>User ID</th>
                        <th>Username</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Role</th>
                        <th>Contact Info</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<User> userList = (List<User>) request.getAttribute("userList");
                        if (userList != null) {
                            for (User user : userList) {
                    %>
                                <tr>
                                    <td><%= user.getUserID() %></td>
                                    <td><%= user.getUsername() %></td>
                                    <td><%= user.getName() %></td>
                                    <td><%= user.getEmail() %></td>
                                    <td><%= user.getRoleID() %></td>
                                    <td><%= user.getContactInfo() %></td>
                                    <td>
                                        <a href="UserController?action=edit&id=<%= user.getUserID() %>" class="edit-btn">Edit</a>
                                        <a href="UserController?action=delete&id=<%= user.getUserID() %>" class="delete-btn" onclick="confirmDelete(event)">Delete</a>
                                    </td>
                                </tr>
                    <%
                            }
                        }
                    %>
                </tbody>
            </table>
            <div class="add-user">
                <a href="UserController?action=new" class="add-btn">Add New User</a>
            </div>
        </section>
    </div>
    <script src="js/manageUser.js"></script>
</body>
</html>
