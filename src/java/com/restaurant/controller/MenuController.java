package com.restaurant.controller;

import com.restaurant.dao.MenuDAO;
import com.restaurant.model.Menu;
import java.io.File;
import java.io.FileOutputStream;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;

@WebServlet("/MenuController")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class MenuController extends HttpServlet {
    private MenuDAO menuDAO;
    private static final String UPLOAD_DIR = "uploads";

    public void init() throws ServletException {
        menuDAO = new MenuDAO(); // Initialize the menuDAO properly here with a connection
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "view":
                    viewMenu(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteMenu(request, response);
                    break;
                case "addMenu":
                    addMenu(request, response);
                    break;
                case "updateMenu":
                    updateMenu(request, response);
                    break;
                case "list":
                    listMenus(request, response);
                    break;
                default:
                    listMenus(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listMenus(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Menu> listMenu = menuDAO.getAllMenus();
        request.setAttribute("listMenu", listMenu);
        RequestDispatcher dispatcher = request.getRequestDispatcher("manageMenu.jsp");
        dispatcher.forward(request, response);
    }

    private void viewMenu(HttpServletRequest request, HttpServletResponse response)
        throws SQLException, ServletException, IOException {
        List<Menu> menuList = menuDAO.getAllMenus();
        request.setAttribute("menuList", menuList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("viewMenu.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("MenuForm.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int menuID = Integer.parseInt(request.getParameter("id"));
        Menu existingMenu = menuDAO.getMenuById(menuID);
        RequestDispatcher dispatcher = request.getRequestDispatcher("MenuForm.jsp");
        request.setAttribute("menu", existingMenu);
        dispatcher.forward(request, response);
    }

    private void addMenu(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        String name = request.getParameter("name");
        String category = request.getParameter("category");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        //String imagePath = request.getParameter("image");

        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;

        System.out.println("Application Path: " + applicationPath);
        System.out.println("Upload File Path: " + uploadFilePath);
        // Create the directory if it doesn't exist
        File uploadDir = new File(uploadFilePath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        // Get the file part from the request
        Part filePart = request.getPart("image");
        String fileName = extractFileName(filePart);
        // Ensure the file name is valid
        if (fileName != null && !fileName.isEmpty()) {
            // Prepare the file path where the file will be saved
            String filePath = uploadFilePath + File.separator + fileName;

        System.out.println("File Path: " + filePath);
            // Save the file manually
            try (InputStream inputStream = filePart.getInputStream();
                 FileOutputStream outputStream = new FileOutputStream(filePath)) {

                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                response.getWriter().println("File uploaded successfully! Saved as " + fileName);
            } catch (IOException e) {
                response.getWriter().println("File upload failed: " + e.getMessage());
            }
        } else {
            response.getWriter().println("File upload failed: invalid file name.");
        }
        Menu newMenu = new Menu();
        newMenu.setName(name);
        newMenu.setCategory(category);
        newMenu.setDescription(description);
        newMenu.setPrice(price);
        newMenu.setImagePath(fileName);

        menuDAO.addMenu(newMenu);
        response.sendRedirect("MenuController?action=list");
    }

    private void updateMenu(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        int menuID = Integer.parseInt(request.getParameter("menuID"));
        String name = request.getParameter("name");
        String category = request.getParameter("category");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        
        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;

        System.out.println("Application Path: " + applicationPath);
        System.out.println("Upload File Path: " + uploadFilePath);
        // Create the directory if it doesn't exist
        File uploadDir = new File(uploadFilePath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        // Get the file part from the request
        Part filePart = request.getPart("image");
        String fileName = extractFileName(filePart);
        // Ensure the file name is valid
        if (fileName != null && !fileName.isEmpty()) {
            // Prepare the file path where the file will be saved
            String filePath = uploadFilePath + File.separator + fileName;

        System.out.println("File Path: " + filePath);
            // Save the file manually
            try (InputStream inputStream = filePart.getInputStream();
                 FileOutputStream outputStream = new FileOutputStream(filePath)) {

                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                response.getWriter().println("File uploaded successfully! Saved as " + fileName);
            } catch (IOException e) {
                response.getWriter().println("File upload failed: " + e.getMessage());
            }
        } else {
            response.getWriter().println("File upload failed: invalid file name.");
        }

        Menu menu = new Menu();
        menu.setMenuID(menuID);
        menu.setName(name);
        menu.setCategory(category);
        menu.setDescription(description);
        menu.setPrice(price);
        menu.setImagePath(fileName);

        menuDAO.updateMenu(menu);
        response.sendRedirect("MenuController?action=list");
    }

    private void deleteMenu(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int menuID = Integer.parseInt(request.getParameter("id"));
        menuDAO.deleteMenu(menuID);
        response.sendRedirect("MenuController?action=list");
    }
    
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }
}
