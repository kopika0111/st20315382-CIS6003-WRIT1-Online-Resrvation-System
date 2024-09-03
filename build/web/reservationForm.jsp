<%@page import="com.restaurant.model.User"%>
<%@page import="com.restaurant.model.Reservation"%>
<%@page import="com.restaurant.model.Table"%>
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
    Reservation reservation = (Reservation) request.getAttribute("reservation");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Make a Reservation</title>
    <link rel="stylesheet" href="css/reservationForm.css">
</head>
<body>
    <% 
        //Reservation reservation = (Reservation) request.getAttribute("reservation");
    %>
    <div class="container">
        <header>
            <h1>Make a Reservation</h1>
            <a href="dashboard.jsp" class="back-btn">Back to Dashboard</a>
        </header>
        <section class="form-section">
            <form action="ReservationController" method="post">
                <input type="hidden" name="action" value="<%= reservation != null ? "updateReservation" : "submitReservation" %>">
                <input type="hidden" name="reservationID" value="<%= reservation != null ? reservation.getReservationID() : "" %>">
                
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
                        <option value="<%= customer.getUserID() %>" <%= request.getAttribute("reservation") != null && customer.getUserID() == ((Reservation) request.getAttribute("reservation")).getCustomerID() ? "selected" : "" %>>
                            <%= customer.getName() %>
                        </option>
                    <% 
                            }
                        }
                    %>
                </select>
                <% } %>
                <label for="reservationType">Reservation Type:</label>
                <select id="reservationType" name="reservationType" required>
                    <option value="">--Select Type--</option>
                    <option value="Dine-In" <%= reservation != null && "Dine-In".equals(((Reservation) request.getAttribute("reservation")).getReservationType()) ? "selected" : "" %> >Dine-In</option>
                    <option value="Takeaway" <%= reservation != null && "Takeaway".equals(((Reservation) request.getAttribute("reservation")).getReservationType()) ? "selected" : "" %> >Takeaway</option>
                    <option value="Delivery" <%= reservation != null && "Delivery".equals(((Reservation) request.getAttribute("reservation")).getReservationType()) ? "selected" : "" %> >Delivery</option>
                </select>
                
                <label for="tableID">Table:</label>
                <select id="tableID" name="tableID" required>
                    <option value="">--Select Table--</option>
                    <% 
                        List<Table> tableList = (List<Table>) request.getAttribute("tableList");
                        if (tableList != null) {
                            for (Table table : tableList) {
                    %>
                        <option value="<%= table.getTableID() %>" <%= request.getAttribute("reservation") != null && table.getTableID() == ((Reservation) request.getAttribute("reservation")).getTableID() ? "selected" : "" %>>
                            Table <%= table.getTableID() %> (Capacity: <%= table.getCapacity() %>)
                        </option>
                    <% 
                            }
                        }
                    %>
                </select>
                
                <label for="reservationTime">Reservation Time:</label>
                <input type="datetime-local" id="reservationTime" name="reservationTime" value="<%= reservation != null ? reservation.getReservationTime() : "" %>" required>
                
                <label for="status">Status:</label>
                <select id="status" name="status" required>
                     <option value="">--Select Status--</option>
                    <option value="Pending" <%= reservation != null && "Pending".equals(((Reservation) request.getAttribute("reservation")).getStatus()) ? "selected" : "" %> >Pending</option>
                    <option value="Confirmed" <%= reservation != null && "Confirmed".equals(((Reservation) request.getAttribute("reservation")).getStatus()) ? "selected" : "" %>>Confirmed</option>
                    <option value="Cancelled" <%= reservation != null && "Cancelled".equals(((Reservation) request.getAttribute("reservation")).getStatus()) ? "selected" : "" %>>Cancelled</option>
                </select>
                
                <label for="paymentStatus">Payment Status:</label>
                <select id="paymentStatus" name="paymentStatus" required>
                     <option value="">--Select Payment Status--</option>
                    <option value="Unpaid" <%= reservation != null && "Unpaid".equals(((Reservation) request.getAttribute("reservation")).getPaymentStatus()) ? "selected" : "" %>>Unpaid</option>
                    <option value="Paid" <%= reservation != null && "Paid".equals(((Reservation) request.getAttribute("reservation")).getPaymentStatus()) ? "selected" : "" %>>Paid</option>
                </select>
                
                <button type="submit" class="submit-btn">Reserve</button>
            </form>
        </section>
    </div>
</body>
</html>
