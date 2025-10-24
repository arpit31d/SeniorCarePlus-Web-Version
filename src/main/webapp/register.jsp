<!DOCTYPE html>
<html>
<head>
  <title>Register</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
  <h2 class="text-center mb-4">Register</h2>
  <form method="post" action="RegisterServlet">
    <input class="form-control mb-2" name="name" placeholder="Name" required>
    <input class="form-control mb-2" name="username" placeholder="Username" required>
    <input type="password" class="form-control mb-2" name="password" placeholder="Password" required>
    <input type="email" class="form-control mb-2" name="email" placeholder="Your Email" required>
    <button class="btn btn-success w-100" type="submit">Register</button>
  </form>
  <a href="login.jsp" class="d-block mt-3 text-center">Back to Login</a>
</div>
</body>
</html>
