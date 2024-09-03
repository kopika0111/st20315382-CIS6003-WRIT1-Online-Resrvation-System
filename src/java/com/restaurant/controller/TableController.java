package com.restaurant.controller;

import com.restaurant.dao.TableDAO;
import com.restaurant.model.Table;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.annotation.WebServlet;

@WebServlet("/TableController")
public class TableController extends HttpServlet {

    private TableDAO tableDAO;

    @Override
    public void init() {
        tableDAO = new TableDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "insert":
                    insertTable(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "update":
                    updateTable(request, response);
                    break;
                case "delete":
                    deleteTable(request, response);
                    break;
                default:
                    listTables(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listTables(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Table> tableList = tableDAO.getAllTables();
        request.setAttribute("tableList", tableList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("tableList.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("tableForm.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int tableID = Integer.parseInt(request.getParameter("id"));
        Table existingTable = tableDAO.getAllTables().stream()
                                      .filter(t -> t.getTableID() == tableID)
                                      .findFirst()
                                      .orElse(null);
        RequestDispatcher dispatcher = request.getRequestDispatcher("tableForm.jsp");
        request.setAttribute("table", existingTable);
        dispatcher.forward(request, response);
    }

    private void insertTable(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int capacity = Integer.parseInt(request.getParameter("capacity"));
        String status = request.getParameter("status");
        Table newTable = new Table();
        newTable.setCapacity(capacity);
        newTable.setStatus(status);
        tableDAO.addTable(newTable);
        response.sendRedirect("TableController?action=list");
    }

    private void updateTable(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int tableID = Integer.parseInt(request.getParameter("tableID"));
        int capacity = Integer.parseInt(request.getParameter("capacity"));
        String status = request.getParameter("status");

        Table table = new Table();
        table.setTableID(tableID);
        table.setCapacity(capacity);
        table.setStatus(status);
        tableDAO.updateTable(table);
        response.sendRedirect("TableController?action=list");
    }

    private void deleteTable(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int tableID = Integer.parseInt(request.getParameter("id"));
        tableDAO.deleteTable(tableID);
        response.sendRedirect("TableController?action=list");
    }
}
