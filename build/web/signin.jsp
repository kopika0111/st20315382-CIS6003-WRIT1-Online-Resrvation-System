<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sign In</title>
    <link rel="stylesheet" href="css/signin.css">
</head>
<body>
    <div class="signin-container">
        <h2>Sign In</h2>
        <form action="AuthController" method="post">
            <div class="input-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="input-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="input-group">
                <button type="submit">Sign In</button>
            </div>
            <div class="error-message">
                <%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "" %>
            </div>
        </form>
        <div style="text-align: center; margin-top: 20px; font-size: 14px;">
            <p>Don't have an account? <a href="signup.jsp">Create a new account</a></p>
        </div>
    </div>
    
</body>
</html>
