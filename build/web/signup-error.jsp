<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sign Up Error</title>
    <link rel="stylesheet" href="css/signup-error.css">
</head>
<body>
    <div class="container">
        <h2>Sign Up Error</h2>
        <p>Something went wrong during the sign-up process. Please try again.</p>
        <div class="error-message">
            <%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "An unexpected error occurred." %>
        </div>
        <div class="button-group">
            <a href="signup.jsp" class="button">Try Again</a>
            <a href="home.jsp" class="button">Back to Home</a>
        </div>
    </div>
</body>
</html>
