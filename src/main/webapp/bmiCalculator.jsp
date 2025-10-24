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
  <title>Calculate BMI</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
  <h2>BMI Calculator</h2>
  <form action="BMIProcessingServlet" method="post" class="row g-3">
    <div class="col-md-6">
      <input type="number" step="0.1" class="form-control" name="height" placeholder="Height (cm)" required>
    </div>
    <div class="col-md-6">
      <input type="number" step="0.1" class="form-control" name="weight" placeholder="Weight (kg)" required>
    </div>
    <div class="col-12">
      <button class="btn btn-secondary" type="submit">Calculate</button>
      <a href="index.jsp" class="btn btn-outline-secondary ms-2">Back</a>
    </div>
  </form>

  <% if (session.getAttribute("bmi") != null && session.getAttribute("status") != null) { %>
    <hr>
    <h4>Results</h4>
    <p><strong>BMI:</strong> <%= session.getAttribute("bmi") %></p>
    <p><strong>Health Status:</strong> <%= session.getAttribute("status") %></p>
  <% } %>
</div>
</body>
</html>
