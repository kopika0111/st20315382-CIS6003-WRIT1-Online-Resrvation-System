<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List"%>
<%@ page import="com.restaurant.model.Menu"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manage Menu</title>
    <link rel="stylesheet" href="css/manageMenu.css">
</head>
<script>
    function searchMenu() {
        // Declare variables
        var input, filter, table, tr, td, i, txtValue;
        input = document.getElementById("searchInput");
        filter = input.value.toLowerCase();
        table = document.getElementById("menuTable");
        tr = table.getElementsByTagName("tr");

        // Loop through all table rows, and hide those who don't match the search query
        for (i = 1; i < tr.length; i++) { // Start from 1 to skip the header row
            tr[i].style.display = "none";
            td = tr[i].getElementsByTagName("td");
            for (var j = 0; j < td.length; j++) {
                if (td[j]) {
                    txtValue = td[j].textContent || td[j].innerText;
                    if (txtValue.toLowerCase().indexOf(filter) > -1) {
                        tr[i].style.display = "";
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
            <h1>Manage Menu</h1>
            <a href="dashboard.jsp" class="back-btn">Back to Dashboard</a>
            <a href="MenuController?action=new" class="add-btn">Add New Menu</a>
        </header>
        <section class="menu-list">
            <!-- Search box -->
            <input type="text" id="searchInput" onkeyup="searchMenu()" placeholder="Search for menu items.." class="search-box">
            <table id="menuTable">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Category</th>
                        <th>Description</th>
                        <th>Price</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Menu> listMenu = (List<Menu>) request.getAttribute("listMenu");
                        if (listMenu != null) {
                            for (Menu menu : listMenu) {
                    %>
                                <tr>
                                    <td><%= menu.getMenuID() %></td>
                                    <td><%= menu.getName() %></td>
                                    <td><%= menu.getCategory() %></td>
                                    <td><%= menu.getDescription() %></td>
                                    <td><%= menu.getPrice() %></td>
                                    <td>
                                        <a href="MenuController?action=edit&id=<%= menu.getMenuID() %>" class="edit-btn">Edit</a>
                                        <a href="MenuController?action=delete&id=<%= menu.getMenuID() %>" class="delete-btn" onclick="return confirm('Are you sure you want to delete this menu?');">Delete</a>
                                    </td>
                                </tr>
                    <%
                            }
                        }
                    %>
                </tbody>
            </table>
        </section>
    </div>
</body>
</html>
