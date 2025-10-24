<%@ page session="true" %>
<%
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
  <title>Appointment Scheduling</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
  <h2>Schedule Appointment</h2>
  <form action="AppointmentServlet" method="post" class="row g-3">
    <div class="col-md-6">
      <input type="email" class="form-control" name="doctorEmail" placeholder="Doctor's Email" required>
    </div>
    <div class="col-md-6">
      <input type="datetime-local" class="form-control" name="dateTime" required>
    </div>
    
    <input type="hidden" name="email" value="<%= session.getAttribute("username") %>">
    
    <div class="col-12">
      <button class="btn btn-info" type="submit">Schedule</button>
      <a href="index.jsp" class="btn btn-outline-secondary ms-2">Back</a>
    </div>
  </form>
</div>
</body>
</html>
