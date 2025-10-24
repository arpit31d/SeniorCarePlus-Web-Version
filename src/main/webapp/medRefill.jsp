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
  <title>Medication Refill Reminder</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body class="bg-light">
  <div class="container mt-5" style="max-width: 600px;">
    <h2>Set Medication Refill Reminder</h2>
    <form action="med-refill" method="post">
    <input type="hidden" name="userId" value="<%= session.getAttribute("userId") %>"/>
    <div class="mb-3">
        <label for="email" class="form-label">Email to Notify</label>
        <input type="email" id="email" name="email" class="form-control" required />
    </div>
    <div class="mb-3">
        <label for="medication_name" class="form-label">Medicine Name</label>
        <input type="text" id="medication_name" name="medication_name" class="form-control" required />
    </div>
    <div class="mb-3">
        <label for="total_pills" class="form-label">Total Pills (stock)</label>
        <input type="number" id="total_pills" name="total_pills" class="form-control" min="1" required />
    </div>
    <div class="mb-3">
        <label for="dosage_per_intake" class="form-label">Dose Per Intake</label>
        <input type="number" id="dosage_per_intake" name="dosage_per_intake" class="form-control" min="1" required />
    </div>
    <div class="mb-3">
        <label for="frequency_per_day" class="form-label">Times Per Day</label>
        <input type="number" id="frequency_per_day" name="frequency_per_day" class="form-control" min="1" required />
    </div>
    <div class="mb-3">
        <label for="start_date" class="form-label">Start Date</label>
        <input type="date" id="start_date" name="start_date" class="form-control" required />
    </div>
    <button type="submit" class="btn btn-primary w-100">Subscribe</button>
</form>


    <%
      String msg = request.getParameter("msg");
      if (msg != null) {
    %>
    <div class="alert alert-success mt-3"><%= msg %></div>
    <% } %>
  </div>
</body>
</html>
