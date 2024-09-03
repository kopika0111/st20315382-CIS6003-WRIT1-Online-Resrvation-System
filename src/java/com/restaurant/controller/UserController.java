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
import com.restaurant.dao.UserDAO;
import com.restaurant.model.User;
import java.io.File;
import java.io.FileOutputStream;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;

@WebServlet("/UserController")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class UserController extends HttpServlet {
    private static final String UPLOAD_DIR = "uploads";
    private UserDAO userDAO;

    public void init() throws ServletException {
        try {
            userDAO = new UserDAO();
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "list":
                    listUsers(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "new":
                    showNewForm(request, response);
                    break;
                case "delete":
                    deleteUser(request, response);
                    break;
                case "signup":
                    signUpUser(request, response);
                    break;
                case "addUser":
                    addUser(request, response);
                    break;
                case "updateUser":
                    updateUser(request, response);
                    break;
                default:
                    listUsers(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<User> userList = userDAO.getAllUsers();
        
        request.setAttribute("userList", userList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("manageUsers.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        //int id = Integer.parseInt(request.getParameter("id"));
        RequestDispatcher dispatcher = request.getRequestDispatcher("userForm.jsp");
        dispatcher.forward(request, response);
    }
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User existingUser = userDAO.getUserById(id);

        // Set the user as a request attribute and forward to JSP
        request.setAttribute("user", existingUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("userForm.jsp");
        dispatcher.forward(request, response);
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        userDAO.deleteUser(id);
        response.sendRedirect("UserController?action=list");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    private void signUpUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String name = request.getParameter("name");
        String contactInfo = request.getParameter("contactInfo");
        
        
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
        Part filePart = request.getPart("file");
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
        
        System.out.println("sigup file : " + fileName);
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setName(name);
        user.setImagePath(fileName);
        user.setContactInfo(contactInfo);
        user.setRoleID(3);  // Assuming 3 is the default role for a new user (Customer)

        try {
            UserDAO userDAO = new UserDAO();
            userDAO.addUser(user);
            response.sendRedirect("signup-success.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("signup-error.jsp");
        }
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
    
    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int userID = Integer.parseInt(request.getParameter("userID"));
        String username = request.getParameter("username");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String contactInfo = request.getParameter("contactInfo");
        int roleID = Integer.parseInt(request.getParameter("roleID"));

        User user = new User();
        user.setUserID(userID);
        user.setUsername(username);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setContactInfo(contactInfo);
        user.setRoleID(roleID);

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
        user.setImagePath(fileName);
        userDAO.updateUser(user);

        response.sendRedirect("UserController?action=list");
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        String username = request.getParameter("username");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String contactInfo = request.getParameter("contactInfo");
        int roleID = Integer.parseInt(request.getParameter("roleID"));

        User user = new User();
        user.setUsername(username);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setContactInfo(contactInfo);
        user.setRoleID(roleID);

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
        user.setImagePath(fileName);

        userDAO.addUser(user);

        response.sendRedirect("UserController?action=list");
    }

}

