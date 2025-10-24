<%@ page import="util.VitalSignDAO" %>
<%@ page import="com.example.seniorcare.model.VitalSign" %>
<%@ page session="true" %>
<%
    Object userIdObj = session.getAttribute("userId");
    if (userIdObj == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    int userId = (Integer) userIdObj;

    VitalSignDAO dao = new VitalSignDAO();
    java.util.List<VitalSign> vitals = dao.getVitalsByUserId(userId);
%>
<!DOCTYPE html>
<html>
<head>
    <title>Vital Signs History</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body class="bg-light">
<div class="container mt-5" style="max-width: 900px;">

<h2>Vital Signs History</h2>

<table class="table table-striped table-bordered">
    <thead>
        <tr>
            <th>Timestamp</th>
            <th>Systolic</th>
            <th>Diastolic</th>
            <th>Heart Rate</th>
            <th>Blood Sugar</th>
            <th>SpO2</th>
        </tr>
    </thead>
    <tbody>
    <%
        if (vitals == null || vitals.isEmpty()) {
    %>
        <tr><td colspan="6" class="text-center">No vital signs logged yet.</td></tr>
    <%
        } else {
            for (VitalSign v : vitals) {
    %>
        <tr>
            <td><%= v.getTimestamp() %></td>
            <td><%= v.getSystolic() %></td>
            <td><%= v.getDiastolic() %></td>
            <td><%= v.getHeartRate() %></td>
            <td><%= v.getBloodSugar() %></td>
            <td><%= v.getSpo2() %></td>
        </tr>
    <%
            }
        }
    %>
    </tbody>
</table>

<a href="logvitals.jsp" class="btn btn-primary">Log More Vitals</a>

</div>
</body>
</html>
