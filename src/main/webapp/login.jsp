<!DOCTYPE html>
<html>
<head>
  <title>Login</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
  <div class="container mt-5">
    <h2 class="text-center mb-4">Login</h2>
    <form method="post" action="LoginServlet">
      <input class="form-control mb-3" name="username" placeholder="Username" required>
      <input type="password" class="form-control mb-3" name="password" placeholder="Password" required>
      <button class="btn btn-primary w-100" type="submit">Login</button>
    </form>
    <a href="register.jsp" class="d-block mt-3 text-center">Register here</a>
    <% if(request.getParameter("error") != null) { %>
      <div class="alert alert-danger mt-3"><%= request.getParameter("error") %></div>
    <% } %>
  </div>
</body>
</html>
