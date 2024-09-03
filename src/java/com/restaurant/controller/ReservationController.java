package com.restaurant.controller;

import com.restaurant.dao.ReservationDAO;
import com.restaurant.dao.TableDAO;
import com.restaurant.dao.UserDAO;
import com.restaurant.model.Reservation;
import com.restaurant.model.Table;
import com.restaurant.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;

@WebServlet("/ReservationController")
public class ReservationController extends HttpServlet {
    private ReservationDAO reservationDAO;
    private TableDAO tableDAO;
    private UserDAO userDAO;

    public void init() throws ServletException {
        reservationDAO = new ReservationDAO(); // Initialize the menuDAO properly here with a connection
        tableDAO = new TableDAO();
        try {
            userDAO = new UserDAO();
        } catch (SQLException ex) {
            Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("signin.jsp");
            return;
        }
        
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "submitReservation":
                    submitReservation(request, response);
                    break;
                case "updateReservation":
                    updateReservation(request, response);
                    break;
                case "new":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteReservation(request, response);
                    break;
                case "list":
                    listReservations(request, response);
                    break;
                default:
                    listReservations(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
    
    private void submitReservation(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int customerID = Integer.parseInt(request.getParameter("customerID"));
        String reservationType = request.getParameter("reservationType");
        String reservationTime = request.getParameter("reservationTime");
        String status = request.getParameter("status");
        String paymentStatus = request.getParameter("paymentStatus");
        int tableID = Integer.parseInt(request.getParameter("tableID"));
        
        Reservation reservation = new Reservation();
        reservation.setCustomerID(customerID);
        reservation.setReservationType(reservationType);
        reservation.setReservationTime(reservationTime);
        reservation.setStatus(status);
        reservation.setPaymentStatus(paymentStatus);
        reservation.setTableID(tableID);

        reservationDAO.createReservation(reservation);
        response.sendRedirect("ReservationController?action=list");
    }

    private void listReservations(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Reservation> reservartionList = reservationDAO.getAllReservations();
        request.setAttribute("reservartionList", reservartionList);
        System.out.println("list reservation : " + reservartionList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("manageReservation.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int reservationID = Integer.parseInt(request.getParameter("id"));
        Reservation reservation = reservationDAO.getReservationById(reservationID);
        List<User> customerList = userDAO.getAllCustomers();
        List<Table> tableList = tableDAO.getAllTables();
        request.setAttribute("reservation", reservation);
        request.setAttribute("tableList", tableList);
        request.setAttribute("customerList", customerList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("reservationForm.jsp");
        dispatcher.forward(request, response);
    }

    private void updateReservation(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int reservationID = Integer.parseInt(request.getParameter("reservationID"));
        String reservationType = request.getParameter("reservationType");
        String reservationTime = request.getParameter("reservationTime");
        String status = request.getParameter("status");
        String paymentStatus = request.getParameter("paymentStatus");
        int tableID = Integer.parseInt(request.getParameter("tableID"));

        Reservation reservation = new Reservation();
        reservation.setReservationID(reservationID);
        reservation.setReservationType(reservationType);
        reservation.setReservationTime(reservationTime);
        reservation.setStatus(status);
        reservation.setPaymentStatus(paymentStatus);
        reservation.setTableID(tableID);

        reservationDAO.updateReservation(reservation);
        response.sendRedirect("ReservationController?action=list");
    }

    private void deleteReservation(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int reservationID = Integer.parseInt(request.getParameter("id"));
        reservationDAO.deleteReservation(reservationID);
        response.sendRedirect("ReservationController?action=list");
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, SQLException, IOException {
        List<Table> tableList = tableDAO.getAllTables();
        List<User> customerList = userDAO.getAllCustomers();
        request.setAttribute("tableList", tableList);
        request.setAttribute("customerList", customerList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("reservationForm.jsp");
        dispatcher.forward(request, response);
    }
}