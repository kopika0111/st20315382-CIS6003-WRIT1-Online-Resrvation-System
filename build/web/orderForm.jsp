<%@page import="com.restaurant.model.OrderItem"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.restaurant.model.Menu"%>
<%@page import="com.restaurant.model.Table"%>
<%@page import="java.util.List"%>
<%@page import="com.restaurant.model.User"%>
<%@page import="com.restaurant.model.Order"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
//    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute("user") == null) {
        response.sendRedirect("signin.jsp");
        return;
    }
    User user = (User) session.getAttribute("user");
    Order order = (Order) request.getAttribute("order");
    
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add/Update Order</title>
    <link rel="stylesheet" href="css/orderForm.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <div class="container">
        <h1><%= order != null ? "Update Order" : "Add Order" %></h1>
        <form action="OrderController" method="post">
            <input type="hidden" name="action" value="<%= order != null ? "update" : "insert" %>">
            <input type="hidden" name="id" value="<%= order != null ? order.getID() : "" %>">
            
            <% if(user.getRoleID() == 3) { %>
                <input type="hidden" id="customerID" name="customerID" value="<%= user.getUserID() %>" readonly>
            <% } else { %>
                <label for="customerID">Customer:</label>
                <select id="customerID" name="customerID" required>
                    <option value="">--Select Customer--</option>
                    <% 
                        List<User> customerList = (List<User>) request.getAttribute("customerList");
                        if (customerList != null) {
                            for (User customer : customerList) {
                    %>
                        <option value="<%= customer.getUserID() %>" <%= order != null && customer.getUserID() == order.getCustomerID() ? "selected" : "" %>>
                            <%= customer.getName() %>
                        </option>
                    <% 
                            }
                        }
                    %>
                </select>
            <% } %>

            <label for="orderType">Order Type:</label>
            <select id="orderType" name="orderType" required>
                <option value="">--Select Type--</option>
                <option value="Dine-In" <%= order != null && "Dine-In".equals(order.getOrderType()) ? "selected" : "" %>>Dine-In</option>
                <option value="Takeaway" <%= order != null && "Takeaway".equals(order.getOrderType()) ? "selected" : "" %>>Takeaway</option>
                <option value="Delivery" <%= order != null && "Delivery".equals(order.getOrderType()) ? "selected" : "" %>>Delivery</option>
            </select>

            <label for="tableID">Table:</label>
            <select id="tableID" name="tableID" required>
                <option value="">--Select Table--</option>
                <% 
                    List<Table> tableList = (List<Table>) request.getAttribute("tableList");
                    if (tableList != null) {
                        for (Table table : tableList) {
                %>
                    <option value="<%= table.getTableID() %>" <%= order != null && table.getTableID() == order.getTableID() ? "selected" : "" %>>
                        Table <%= table.getTableID() %> (Capacity: <%= table.getCapacity() %>)
                    </option>
                <% 
                        }
                    }
                %>
            </select>

            <label for="menuItem">Select Items:</label>
            <div id="menuItems">
                <% 
                    List<Menu> menuItems = (List<Menu>) request.getAttribute("menuItems");
                    if (menuItems != null) {
                        for (Menu item : menuItems) {
                %>
                    <img src="<%= "uploads/" + item.getImagePath() %>" alt="<%= item.getName() %>" class="menu-item-image">
                    <div class="menu-item">
                        <input type="checkbox" class="item-checkbox" data-price="<%= item.getPrice() %>" name="itemID" value="<%= item.getMenuID() %>">
                        <span><%= item.getName() %> - $<%= item.getPrice() %></span>
                        <input type="number" class="item-quantity" name="itemQuantity_<%= item.getMenuID() %>" value="1" min="1" disabled>
                    </div>
                <% 
                        }
                    }
                %>
            </div>

            <label for="total">Total:</label>
            <input type="text" id="total" name="total" value="<%= order != null ? order.getTotal() : "" %>" readonly>

            <button type="submit"><%= order != null ? "Update Order" : "Add Order" %></button>
        </form>
    </div>
    <script>
        $(document).ready(function() {
            $('.item-checkbox').change(function() {
                let total = 0;
                $('.item-checkbox:checked').each(function() {
                    let price = $(this).data('price');
                    let quantityInput = $(this).closest('.menu-item').find('.item-quantity');
                    quantityInput.prop('disabled', false);
                    let quantity = quantityInput.val();
                    total += price * quantity;
                });
                $('#total').val(total.toFixed(2));
            });

            $('.item-quantity').on('input', function() {
                let total = 0;
                $('.item-checkbox:checked').each(function() {
                    let price = $(this).data('price');
                    let quantityInput = $(this).closest('.menu-item').find('.item-quantity');
                    let quantity = quantityInput.val();
                    total += price * quantity;
                });
                $('#total').val(total.toFixed(2));
            });
        });
    </script>
</body>
</html>
