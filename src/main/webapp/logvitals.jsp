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
    <title>Log Vital Signs</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body class="bg-light">
<div class="container mt-5" style="max-width: 700px;">

    <h2>Log Vital Signs</h2>
    <form action="vitals" method="post" class="mb-4">
        <input type="hidden" name="user" value="<%= session.getAttribute("userId") %>" />
        <div class="row g-3">
            <div class="col-md-6">
                <input type="number" name="systolic" class="form-control" placeholder="Systolic (mmHg)" min="50" max="300" step="1" />
            </div>
            <div class="col-md-6">
                <input type="number" name="diastolic" class="form-control" placeholder="Diastolic (mmHg)" min="30" max="200" step="1" />
            </div>
            <div class="col-md-6">
                <input type="number" name="heartrate" class="form-control" placeholder="Heart Rate (bpm)" min="30" max="220" step="1" />
            </div>
            <div class="col-md-6">
                <input type="number" name="bloodsugar" class="form-control" placeholder="Blood Sugar (mg/dL)" min="40" max="600" step="1" />
            </div>
            <div class="col-md-6">
                <input type="number" name="spo2" class="form-control" placeholder="SpO2 (%)" min="70" max="100" step="1" />
            </div>
        </div>
        <button type="submit" class="btn btn-primary mt-3 w-100">Save Vital</button>
    </form>

</div>
</body>
</html>
