<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.restaurant.model.User" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">

<%
//    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute("user") == null) {
        response.sendRedirect("signin.jsp");
        return;
    }
    User user = (User) session.getAttribute("user");
    String imagePath = "uploads/" + user.getImagePath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
    <link rel="stylesheet" href="css/dashboard.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>Welcome, <%= user.getName() %></h1>
            <div class="header-right">
                <button id="toggle-nav" class="toggle-nav-btn">☰</button>
                <img src="<%= imagePath %>" alt="Profile Picture" class="profile-pic">
                <a href="logout.jsp" class="logout-btn" onclick="confirmLogout()">Logout</a>
            </div>
        </header>
        <% if(user.getRoleID() == 3) { %>
        <div class="decorative-banner">
            <div class="banner-text">
                <h2>Your Favorite Dining Experience Awaits</h2>
            </div>
            <div class="banner-overlay"></div>
        </div>
         <% } %>
        <nav id="sidebar" class="nav-hidden">
            <ul>
                <li><a href="OrderController?action=list">View Order</a></li>
                <li><a href="ReservationController?action=list">View Reservations</a></li>
                <li><a href="MenuController?action=view">View Menu</a></li>
                <% if (user.getRoleID() == 1) { %>
                    <li><a href="UserController?action=list">Manage Users</a></li>
                    <li><a href="MenuController?action=list">Manage Menu</a></li>
                    <li><a href="TableController?action=list">Manage Table</a></li>
                <% } else if (user.getRoleID() == 2) { %>
                    
                <% } %>
                <% if (user.getRoleID() == 3) { %>
                
                <% } %>
                <li><a href="profile.jsp">Profile</a></li>
            </ul>
        </nav>   
        <section class="content content-shown" id="content">
            <h2>Dashboard</h2>
            <p>Select an option from the menu to proceed.</p>
            <% if(user.getRoleID() != 3) { //System.out.println("dashboard count:" + request.getAttribute("userCount"));%>
            <div class="dashboard-grid">
                <div class="dashboard-item">
                    <h3>Users</h3>
                    <p><i class="fas fa-users"></i> <%= session.getAttribute("userCount") %> users</p>
                    <!-- Add status or more info if needed -->
                </div>
                <div class="dashboard-item">
                    <h3>Orders</h3>
                    <p><i class="fas fa-utensils"></i> <%= session.getAttribute("orderCount") %> orders</p>
                    <!-- Add status or more info if needed -->
                </div>
                <div class="dashboard-item">
                    <h3>Reservations</h3>
                    <p><i class="fas fa-calendar-alt"></i> <%= session.getAttribute("reservationCount") %> reservations</p>
                </div>
            </div>
            <% } %>
        </section>
        <footer>
            <div class="decorative-footer">
            <p>&copy; 2024 ABC Restaurant. All rights reserved.</p>
            <div class="footer-icons">
                <i class="fas fa-leaf"></i>
                <i class="fas fa-utensils"></i>
                <i class="fas fa-mug-hot"></i>
            </div>
        </div>
        </footer>
    </div>
    <script src="js/dashboard.js"></script>
</body>
</html>
