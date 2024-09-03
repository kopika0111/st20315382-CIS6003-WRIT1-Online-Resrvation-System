package com.restaurant.controller;

import com.restaurant.dao.MenuDAO;
import com.restaurant.dao.OrderDAO;
import com.restaurant.dao.OrderItemDAO;
import com.restaurant.dao.TableDAO;
import com.restaurant.dao.UserDAO;
import com.restaurant.model.Menu;
import com.restaurant.model.Order;
import com.restaurant.model.OrderItem;
import com.restaurant.model.Table;
import com.restaurant.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/OrderController")
public class OrderController extends HttpServlet {
    private OrderDAO orderDAO;
    private OrderItemDAO orderItemDAO;
    private TableDAO tableDAO;
    private UserDAO userDAO;
    private MenuDAO menuDAO;

    public void init() {
        orderDAO = new OrderDAO();
        orderItemDAO = new OrderItemDAO();
        tableDAO = new TableDAO();
        menuDAO = new MenuDAO();
        try {
            userDAO = new UserDAO();
        } catch (SQLException ex) {
            Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "insert":
                    insertOrder(request, response);
                    break;
                case "delete":
                    deleteOrder(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "update":
                    updateOrder(request, response);
                    break;
                case "list":
                    listOrders(request, response);
                    break;
                default:
                    listOrders(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        } catch (Exception ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void listOrders(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Order> orderList = orderDAO.getAllOrders();
        request.setAttribute("orderList", orderList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("manageOrder.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        List<Table> tableList = tableDAO.getAllTables();
        List<User> customerList = userDAO.getAllCustomers();
        List<Menu> menuItems = menuDAO.getAllItems();
        request.setAttribute("tableList", tableList);
        request.setAttribute("customerList", customerList);
        request.setAttribute("menuItems", menuItems);
        RequestDispatcher dispatcher = request.getRequestDispatcher("orderForm.jsp");
        dispatcher.forward(request, response);
    }

    private void insertOrder(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, Exception {
        int customerID = Integer.parseInt(request.getParameter("customerID"));
        String orderType = request.getParameter("orderType");
        int tableID = Integer.parseInt(request.getParameter("tableID"));
        double total = Double.parseDouble(request.getParameter("total"));

        Order order = new Order();
        order.setCustomerID(customerID);
        order.setOrderType(orderType);
        order.setTableID(tableID);
        order.setTotal(total);

        int orderID = orderDAO.addOrder(order);

        order.setID(orderID);
        System.out.println("last insert id : " + orderID);
        handleOrderItems(request, orderID);
        response.sendRedirect("OrderController?action=list");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int orderID = Integer.parseInt(request.getParameter("id"));
        List<Table> tableList = tableDAO.getAllTables();
        List<User> customerList = userDAO.getAllCustomers();
        Order existingOrder = orderDAO.getOrderById(orderID);
        List<Menu> menuItems = menuDAO.getAllItems();
//        List<OrderItem> orderItems = orderItemDAO.getOrderItemsByOrderID(orderID);
        request.setAttribute("menuItems", menuItems);
        request.setAttribute("customerList", customerList);
        request.setAttribute("tableList", tableList);
//        request.setAttribute("orderItems", orderItems);
        RequestDispatcher dispatcher = request.getRequestDispatcher("orderForm.jsp");
        request.setAttribute("order", existingOrder);
        dispatcher.forward(request, response);
    }

    private void updateOrder(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, Exception {
        int orderID = Integer.parseInt(request.getParameter("id"));
        int customerID = Integer.parseInt(request.getParameter("customerID"));
        String orderType = request.getParameter("orderType");
        int tableID = Integer.parseInt(request.getParameter("tableID"));
        double total = Double.parseDouble(request.getParameter("total"));

        Order order = new Order();
        order.setID(orderID);
        order.setCustomerID(customerID);
        order.setOrderType(orderType);
        order.setTableID(tableID);
        order.setTotal(total);

        orderDAO.updateOrder(order);
        
        handleOrderItems(request, orderID);
        response.sendRedirect("OrderController?action=list");
    }

    private void deleteOrder(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int orderID = Integer.parseInt(request.getParameter("id"));
        orderDAO.deleteOrder(orderID);
        response.sendRedirect("OrderController?action=list");
    }
    
    private void handleOrderItems(HttpServletRequest request, int orderID) throws Exception {
        // Remove existing order items
        orderItemDAO.deleteOrderItemsByOrderID(orderID);
        
        // Add new order items
        List<Menu> menuItems = menuDAO.getAllItems();
        for (Menu item : menuItems) {
            String itemIDParam = request.getParameter("itemQuantity_" + item.getMenuID());
            if (itemIDParam != null) {
                int quantity = Integer.parseInt(itemIDParam);
                if (quantity > 0) {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrderID(orderID);
                    orderItem.setItemID(item.getMenuID());
                    orderItem.setQty(quantity);
                    orderItemDAO.addOrderItem(orderItem);
                }
            }
        }
    }
}
