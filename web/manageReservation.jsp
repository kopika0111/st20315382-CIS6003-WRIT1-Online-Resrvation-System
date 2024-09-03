<%@page import="com.restaurant.model.User"%>
<%@page import="com.restaurant.model.Reservation"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <title>Manage Reservations</title>
    <link rel="stylesheet" href="css/manageReservation.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
</head>
<script>
        function searchReservations() {
            var input, filter, table, tr, td, i, j, txtValue;
            input = document.getElementById("searchInput");
            filter = input.value.toLowerCase();
            table = document.getElementById("reservationTable");
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
<body>
    <div class="container">
        <header>
            <h1>Manage Reservations</h1>
            <div class="header-buttons">
                <a href="dashboard.jsp" class="back-btn">Back to Dashboard</a>
                <a href="ReservationController?action=new" class="add-btn">Add New Reservation</a>
            </div>
        </header> 
        <!-- Search box -->
        <input type="text" id="searchInput" onkeyup="searchReservations()" placeholder="Search reservations..." class="search-box">
        <section class="reservation-list">
            <table id="reservationTable">
                <thead>
                    <tr>
                        <th>Reservation ID</th>
                        <th>Customer ID</th>
                        <th>Type</th>
                        <th>Time</th>
                        <th>Status</th>
                        <th>Payment Status</th>
                        <th>Table</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Reservation> reservartionList = (List<Reservation>) request.getAttribute("reservartionList");
                        if (reservartionList != null) {
                            for (Reservation reservation : reservartionList) {
                                String display = "";
                                display = user.getRoleID() == 3 && user.getUserID() == reservation.getCustomerID() ? "" : "none";
                                display = user.getRoleID() != 3 ? "" : display;
                    %>
                                <tr style="display: <%= display %>;">
                                    <td><%= reservation.getReservationID()%></td>
                                    <td><%= reservation.getCustomerID()%></td>
                                    <td><%= reservation.getReservationType()%></td>
                                    <td><%= reservation.getReservationTime()%></td>
                                    <td><%= reservation.getStatus()%></td>
                                    <td><%= reservation.getPaymentStatus()%></td>
                                    <td><%=  reservation.getTableID() > 0 ? reservation.getTableID() : "" %></td>
                                    <td>
                                        <a href="ReservationController?action=edit&id=<%= reservation.getReservationID() %>" class="edit-btn">Edit</a>
                                        <a href="ReservationController?action=delete&id=<%= reservation.getReservationID() %>" class="delete-btn" onclick="return confirm('Are you sure you want to delete this menu?');">Delete</a>
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
