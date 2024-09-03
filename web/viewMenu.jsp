<%@page import="com.restaurant.model.Menu"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>View Menu</title>
    <link rel="stylesheet" href="css/viewMenu.css">
    <script>
        function searchMenu() {
            var input, filter, table, tr, td, i, j, txtValue;
            input = document.getElementById("searchInput");
            filter = input.value.toLowerCase();
            table = document.getElementById("menuTable");
            tr = table.getElementsByTagName("tr");

            for (i = 1; i < tr.length; i++) { // Start from 1 to skip the header row
                tr[i].style.display = "none"; // Hide the row by default
                td = tr[i].getElementsByTagName("td");
                for (j = 0; j < td.length; j++) {
                    if (td[j]) {
                        txtValue = td[j].textContent || td[j].innerText;
                        if (txtValue.toLowerCase().indexOf(filter) > -1) {
                            tr[i].style.display = ""; // Show the row if match is found
                            break;
                        }
                    }
                }
            }
        }
    </script>
</head>
<body>
    <div class="container">
        <header>
            <h1>Menu</h1>
            <a href="dashboard.jsp" class="back-btn">Back to Dashboard</a>
        </header>
        <!-- Search box -->
        <input type="text" id="searchInput" onkeyup="searchMenu()" placeholder="Search for menu items..." class="search-box">
        <section class="menu-list">
            <table id="menuTable">
                <thead>
                    <tr>
                        <th></th>
                        <th>Name</th>
                        <th>Category</th>
                        <th>Description</th>
                        <th>Price</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Menu> menuList = (List<Menu>) request.getAttribute("menuList");
                        if (menuList != null) {
                            for (Menu menu : menuList) {
                    %>
                                <tr>
                                    <td><img src="uploads/<%= menu.getImagePath() %>" alt="<%= menu.getName() %>"></td>
                                    <td><%= menu.getName() %></td>
                                    <td><%= menu.getCategory() %></td>
                                    <td><%= menu.getDescription() %></td>
                                    <td>$<%= menu.getPrice() %></td>
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
