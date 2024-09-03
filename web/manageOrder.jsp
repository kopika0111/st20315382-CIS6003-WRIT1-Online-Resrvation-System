<%@page import="java.util.List"%>
<%@page import="com.restaurant.model.Order"%>
<%@page import="com.restaurant.model.Table"%>
<%@page import="com.restaurant.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
//    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute("user") == null) {
        response.sendRedirect("signin.jsp");
        return;
    }
    User user = (User) session.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manage Orders</title>
    <link rel="stylesheet" href="css/manageOrder.css">
    <script>
        function searchOrders() {
            var input, filter, table, tr, td, i, j, txtValue;
            input = document.getElementById("searchInput");
            filter = input.value.toLowerCase();
            table = document.getElementById("orderTable");
            tr = table.getElementsByTagName("tr");

            for (i = 1; i < tr.length; i++) { // Start from 1 to skip the header row
                tr[i].style.display = "none";
                td = tr[i].getElementsByTagName("td");
                for (j = 0; j < td.length; j++) {
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
</head>
<body>
    <div class="container">
        <h1>Manage Orders</h1>
        <a href="OrderController?action=new" class="new-order-btn">New Order</a>
        <!-- Search box -->
        <input type="text" id="searchInput" onkeyup="searchOrders()" placeholder="Search orders..." class="search-box">
        <table id="orderTable">
            <thead>
                <tr>
                    <th>Order ID</th>
                    <th>Customer</th>
                    <th>Order Type</th>
                    <th>Table</th>
                    <th>Total</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    List<Order> orderList = (List<Order>) request.getAttribute("orderList");
                    if (orderList != null) {
                        for (Order order : orderList) {
                            String display = "";
                            display = user.getRoleID() == 3 && user.getUserID() == order.getCustomerID() ? "" : "none";
                            display = user.getRoleID() != 3 ? "" : display;
                %>
                <tr style="display: <%= display %>;">
                    <td><%= order.getID() %></td>
                    <td><%= order.getCustomerID() %></td>
                    <td><%= order.getOrderType() %></td>
                    <td>Table <%= order.getTableID() %></td>
                    <td>$<%= order.getTotal() %></td>
                    <td>
                        <a href="OrderController?action=edit&id=<%= order.getID() %>">Edit</a>
                        <a href="OrderController?action=delete&id=<%= order.getID() %>" onclick="return confirm('Are you sure you want to delete this order?')">Delete</a>
                    </td>
                </tr>
                <% 
                        }
                    } else {
                %>
                <tr>
                    <td colspan="7">No orders found.</td>
                </tr>
                <% 
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
