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
  <title>Medication Reminder</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body class="bg-light">
<div class="container mt-5" style="max-width: 500px;">
  <h2>Set Medication Reminder</h2>

  <div class="alert alert-info" role="alert">
    Mail will be scheduled 10 minutes before medication time.
  </div>

  <form action="MedicationServlet" method="post">
    <div class="mb-3">
      <label for="medName" class="form-label">Medication Name</label>
      <input type="text" id="medName" name="medName" class="form-control" required />
    </div>
    <div class="mb-3">
      <label for="dosage" class="form-label">Dosage</label>
      <input type="text" id="dosage" name="dosage" class="form-control" required />
    </div>
    <div class="mb-3">
      <label for="frequency" class="form-label">Frequency (times per day)</label>
      <input type="number" min="1" id="frequency" name="frequency" class="form-control" required />
    </div>
    <div class="mb-3">
      <label for="reminderTime" class="form-label">Reminder Time</label>
      <input type="time" id="reminderTime" name="reminderTime" class="form-control" required />
    </div>
    <div class="mb-3">
      <label for="stopDate" class="form-label">Stop Date</label>
      <input type="date" id="stopDate" name="stopDate" class="form-control" required />
    </div>

    <button type="submit" class="btn btn-primary w-100">Set Reminder</button>
  </form>
</div>
</body>
</html>
