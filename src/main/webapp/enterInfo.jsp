<%@ page session="true" %>
<%
    if (session.getAttribute("username") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
  <title>Enter Personal Info</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5" style="max-width:400px;">
  <h2>Enter Personal Information</h2>
  <form action="UserInfoServlet" method="post">
    <div class="mb-3">
      <label for="weight" class="form-label">Weight (kg)</label>
      <input type="number" class="form-control" name="weight" id="weight" placeholder="Weight" required>
    </div>
    <div class="mb-3">
      <label for="height" class="form-label">Height (cm)</label>
      <input type="number" class="form-control" name="height" id="height" placeholder="Height" required>
    </div>
    <div class="mb-3">
      <label for="age" class="form-label">Age</label>
      <input type="number" class="form-control" name="age" id="age" placeholder="Age" required>
    </div>
    <div class="mb-3">
      <label for="contact" class="form-label">Contact Number</label>
      <input type="text" class="form-control" name="contact" id="contact" placeholder="Contact Number" required>
    </div>
    <div class="mb-3">
      <label for="email" class="form-label">Email Address</label>
      <input type="email" class="form-control" name="email" id="email" placeholder="Email Address" required>
    </div>
    <div class="mb-3">
      <label for="emergencyEmail" class="form-label">Emergency Contact Email</label>
      <input type="email" class="form-control" name="emergencyEmail" id="emergencyEmail" placeholder="Emergency Email" required>
    </div>
    <button type="submit" class="btn btn-primary w-100">Save Information</button>
  </form>
</div>
</body>
</html>
