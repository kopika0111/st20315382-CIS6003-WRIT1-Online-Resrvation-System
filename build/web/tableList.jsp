<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.restaurant.model.Table"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Tables</title>
    <link rel="stylesheet" href="css/tableList.css">
</head>
<script>
        function searchTables() {
            var input, filter, table, tr, td, i, j, txtValue;
            input = document.getElementById("searchInput");
            filter = input.value.toLowerCase();
            table = document.getElementById("tableList");
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
        <h1>Manage Tables</h1>
        <!-- Search box -->
        <input type="text" id="searchInput" onkeyup="searchTables()" placeholder="Search for tables..." class="search-box">
        <a href="TableController?action=new" class="btn">Add New Table</a>
        <table id="tableList">
            <thead>
                <tr>
                    <th>Table ID</th>
                    <th>Capacity</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Table> tableList = (List<Table>) request.getAttribute("tableList");
                    if (tableList != null) {
                        for (Table table : tableList) {
                %>
                <tr>
                    <td><%= table.getTableID() %></td>
                    <td><%= table.getCapacity() %></td>
                    <td><%= table.getStatus() %></td>
                    <td>
                        <a href="TableController?action=edit&id=<%= table.getTableID() %>" class="btn">Edit</a>
                        <a href="TableController?action=delete&id=<%= table.getTableID() %>" class="btn" onclick="return confirm('Are you sure you want to delete this table?');">Delete</a>
                    </td>
                </tr>
                <%
                        }
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
