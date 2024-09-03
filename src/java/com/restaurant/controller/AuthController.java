/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.restaurant.controller;

/**
 *
 * @author hp
 */

import com.restaurant.dao.OrderDAO;
import com.restaurant.dao.ReservationDAO;
import com.restaurant.dao.UserDAO;
//import com.restaurant.dao.ReservationDAO;
//import com.restaurant.dao.OrderDAO;
import com.restaurant.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;

@WebServlet("/AuthController")
public class AuthController extends HttpServlet {

    private UserDAO userDAO;
    private OrderDAO orderDAO;
    private ReservationDAO reservationDAO;
    public void init() throws ServletException {
        try {
            userDAO = new UserDAO(); // Initialize userDAO properly here
            reservationDAO = new ReservationDAO(); 
            orderDAO = new OrderDAO(); 
        } catch (SQLException ex) {
            Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServletException(ex); // Rethrow as ServletException if initialization fails
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            User user = userDAO.getUserByUsername(username);
            int userCount = userDAO.getUserCount();
            int orderCount = orderDAO.getOrderCount();
            int reservationCount = reservationDAO.getReservationCount();
            
//            request.setAttribute("userCount", userCount);
//            request.setAttribute("orderCount", orderCount);
//            request.setAttribute("reservationCount", reservationCount);
//            System.out.println("signin test count:" + userCount);
            
            if (user != null && user.getPassword().equals(password)) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("userCount", userCount);
                session.setAttribute("orderCount", orderCount);
                session.setAttribute("reservationCount", reservationCount);
                response.sendRedirect("dashboard.jsp");
            } else {
                request.setAttribute("errorMessage", "Invalid username or password");
                request.getRequestDispatcher("signin.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("signin.jsp");
        dispatcher.forward(request, response);
    }
}

