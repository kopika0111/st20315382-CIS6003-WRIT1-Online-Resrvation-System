package com.restaurant.controller;

import com.restaurant.dao.UserDAO;
import com.restaurant.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/ProfileController")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class ProfileController extends HttpServlet {

    private UserDAO userDAO;
    private static final String UPLOAD_DIR = "uploads";

    public void init() {
        try {
            userDAO = new UserDAO();
        } catch (SQLException ex) {
            Logger.getLogger(ProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("signin.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        request.setAttribute("user", user);
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("signin.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        String username = request.getParameter("username");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String contactInfo = request.getParameter("contactInfo");
        String password = request.getParameter("password");
        String roleIdStr = request.getParameter("roleId");
        
        System.out.println("Profile email: " + email);
        int roleId = user.getRoleID(); // Keep the existing role ID by default

        // If the admin changes the role
        if (roleIdStr != null && !roleIdStr.isEmpty() && user.getRoleID() == 1) {
            roleId = Integer.parseInt(roleIdStr);
        }

        // Get the upload directory
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
        Part filePart = request.getPart("profileImage");
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

        user.setUsername(username);
        user.setName(name);
        user.setEmail(email);
        user.setContactInfo(contactInfo);
        user.setRoleID(roleId);

        if (!fileName.isEmpty()) {
            user.setImagePath(fileName); // Assuming you store the filename in the database
        }

        if (password != null && !password.isEmpty()) {
            user.setPassword(password);
        }

        try {
            userDAO.updateUser(user);
            session.setAttribute("user", user);
            request.setAttribute("successMessage", "Profile updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error updating profile. Please try again.");
        }

        request.getRequestDispatcher("profile.jsp").forward(request, response);
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
