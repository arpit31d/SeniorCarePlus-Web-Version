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
    <title>Water Intake Reminder Preferences</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body class="bg-light">
<div class="container mt-5" style="max-width: 500px;">
    <h2>Set Water Intake Reminder</h2>
    <form action="water-prefs" method="post">
        <div class="mb-3">
            <label for="user" class="form-label">User ID</label>
            <input type="text" id="user" name="user" class="form-control" required value="<%= session.getAttribute("userId") %>" readonly>
        </div>
        <div class="mb-3">
            <label for="email" class="form-label">Email for Reminders</label>
            <input type="email" id="email" name="email" class="form-control" required>
        </div>
        <div class="mb-3">
            <label for="intervalMinutes" class="form-label">Reminder Interval (minutes)</label>
            <input type="number" id="intervalMinutes" name="intervalMinutes" class="form-control" min="10" value="60" required>
        </div>
        <div class="mb-3">
            <label for="stopDate" class="form-label">Stop Date (optional)</label>
            <input type="date" id="stopDate" name="stopDate" class="form-control" />
            <small class="text-muted">Reminders will stop after this date.</small>
        </div>
        <button type="submit" class="btn btn-primary w-100">Save Preferences</button>
    </form>

    <%
        String msg = request.getParameter("msg");
        if (msg != null) {
    %>
    <div class="alert alert-success mt-3"><%= msg %></div>
    <%
        }
    %>
</div>
</body>
</html>
