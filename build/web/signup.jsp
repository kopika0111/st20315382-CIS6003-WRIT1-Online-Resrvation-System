<!-- signup.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Sign Up</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
</head>
<body>
    <h2>Sign Up</h2>
    <form action="UserController" method="post" enctype="multipart/form-data">
        <input type="hidden" name="action" value="signup">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required><br><br>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required><br><br>

        <label for="name">Full Name:</label>
        <input type="text" id="name" name="name" required><br><br>

        <label for="contactInfo">Contact No</label>
        <input type="text" id="contactInfo" name="contactInfo"><br><br>
        
        <label>Image Upload</label>
        <input type = "file" name = "file" size = "50" accept="image/*" /><br>
        <br>
        
        <input type="submit" name="action" value="signup">
    </form>
</body>
</html>
