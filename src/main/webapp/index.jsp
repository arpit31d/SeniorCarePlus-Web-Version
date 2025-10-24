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
    <title>SeniorCarePlus - Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: #f8f9fa;
            font-family: 'Segoe UI', Arial, sans-serif;
        }
        .dashboard-container {
            max-width: 560px;
            margin: 40px auto 0 auto;
            background: #fff;
            box-shadow: 0 5px 24px rgba(27,64,142,.07), 0 2px 4px rgba(27,64,142,.03);
            border-radius: 16px;
            padding: 32px 24px 24px 24px;
        }
        .feature-card {
            border: 1px solid #dee2e6;
            border-radius: 10px;
            padding: 20px;
            margin-bottom: 20px;
            background: #f6f7fb;
        }
        .feature-header {
            font-size: 1.2rem;
            font-weight: 600;
            color: #253165;
            margin-bottom: 4px;
        }
        .feature-summary {
            font-size: 0.97rem;
            color: #5b6386;
        }
        .feature-action {
            margin-top: 10px;
        }
        .about-section {
            background: #e6ebff;
            border-radius: 8px;
            padding: 16px 20px;
            margin-bottom: 32px;
        }
        .main-title {
            font-size: 2rem;
            color: #2d387a;
            font-weight: bold;
            margin-bottom: 10px;
            text-align: center;
        }
        .btn-block {
            width: 100%;
        }
        .footer-btns {
            display: flex;
            gap: 12px;
            margin-top: 20px;
            justify-content: center;
        }
    </style>
</head>
<body>
<div class="dashboard-container">

    <div class="about-section">
        <div class="main-title">SeniorCarePlus</div>
        <div>
            <strong>About:</strong>
            <ul style="padding-left:20px;margin-bottom:0;">
                <li><strong>Medication Reminders:</strong> Set daily reminders for medicines with customizable stop dates.</li>
                <li><strong>Medical Records:</strong> Upload, name, and preview prescriptions; track history by date.</li>
                <li><strong>Personal Info:</strong> Enter/edit profile, contacts, emergency email for SOS, and health details.</li>
                <li><strong>SOS Alert:</strong> Send emergency email with your location to your emergency contact email.</li>
                <li><strong>Water Intake Reminder:</strong> Set hydration reminders to receive periodic email notifications.</li>
                <li><strong>Vital Signs Tracking:</strong> Log and visualize vital signs such as blood pressure, heart rate, blood sugar, and oxygen saturation.</li>
                <li><strong>Medication Refill Reminders:</strong> Receive alerts before medication runs out, based on usage rate.</li>
                <li><strong>Schedule Appointments:</strong> Manage and receive reminders for medical appointments.</li>
                <li><strong>BMI Checker:</strong> Calculate your Body Mass Index for health assessment.</li>
            </ul>
        </div>
    </div>

    <div class="feature-card">
        <div class="feature-header">Medication Reminder</div>
        <div class="feature-summary">
            Set daily or scheduled reminders for your medications.
        </div>
        <div class="feature-action">
            <a class="btn btn-outline-primary btn-block" href="medication.jsp">Go to Medication Reminder</a>
        </div>
    </div>

    <div class="feature-card">
        <div class="feature-header">Medical Records</div>
        <div class="feature-summary">
            Upload and manage your prescription records easily.
        </div>
        <div class="feature-action">
            <a class="btn btn-outline-primary btn-block" href="MedicalRecordsListServlet">Manage Medical Records</a>
        </div>
    </div>

    <div class="feature-card">
        <div class="feature-header">Personal Info</div>
        <div class="feature-summary">
            Update personal and emergency contact information.
        </div>
        <div class="feature-action">
            <a class="btn btn-outline-primary btn-block" href="enterInfo.jsp">Edit Personal Info</a>
        </div>
    </div>

    <div class="feature-card">
        <div class="feature-header">SOS Emergency</div>
        <div class="feature-summary">
            Instantly send an SOS message with your real-time location.
        </div>
        <div class="feature-action">
            <button id="sosBtn" class="btn btn-danger btn-block">Send SOS Now</button>
        </div>
    </div>
    
    <div class="feature-card">
        <div class="feature-header">Water Intake Reminder</div>
        <div class="feature-summary">
            Set email reminders to keep yourself hydrated during the day.
        </div>
        <div class="feature-action">
            <a class="btn btn-outline-primary btn-block" href="waterPrefs.jsp">Set Water Intake Reminder</a>
        </div>
    </div>
    
    <div class="feature-card">
        <div class="feature-header">Vital Signs Tracking</div>
        <div class="feature-summary">
            Log and track your vital signs with visual charts.
        </div>
        <div class="feature-action">
            <a class="btn btn-outline-primary btn-block" href="vitals.jsp">Track Vitals</a>
        </div>
    </div>
    
    <div class="feature-card">
        <div class="feature-header">Medication Refill Reminder</div>
        <div class="feature-summary">
            Get notified before your medication runs out.
        </div>
        <div class="feature-action">
            <a class="btn btn-outline-primary btn-block" href="medRefill.jsp">Medication Refill Reminder</a>
        </div>
    </div>
    
    <div class="feature-card">
        <div class="feature-header">Schedule Appointment</div>
        <div class="feature-summary">
            Manage your doctor appointments with reminders.
        </div>
        <div class="feature-action">
            <a class="btn btn-outline-primary btn-block" href="appointments.jsp">Schedule Appointment</a>
        </div>
    </div>
    
    <div class="feature-card">
        <div class="feature-header">BMI Checker</div>
        <div class="feature-summary">
            Calculate your Body Mass Index (BMI) to assess your weight category and health risk.
        </div>
        <div class="feature-action">
            <a class="btn btn-outline-success btn-block" href="bmiCalculator.jsp">Check BMI</a>
        </div>
    </div>
    
    <div class="footer-btns">
        <form action="logout.jsp" method="post" style="display:inline;">
            <button class="btn btn-warning" type="submit">Logout</button>
        </form>
        <a href="https://github.com/arpit31d/SeniorCareWebPage/tree/master" class="btn btn-dark" target="_blank">GitHub Source</a>
    </div>
</div>

<script>
document.getElementById('sosBtn').addEventListener('click', function() {
    if (!navigator.geolocation) {
        alert("Geolocation is not supported by your browser.");
        return;
    }
    navigator.geolocation.getCurrentPosition(
        function(position) {
            fetch('SendSOSServlet', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    latitude: position.coords.latitude,
                    longitude: position.coords.longitude
                })
            })
            .then(resp => resp.json())
            .then(data => {
                if (data.status === "success") alert("SOS Message Sent!");
                else alert("Failed to send SOS: " + (data.message || "Unknown error"));
            })
            .catch(err => alert("Failed to send SOS: " + err.message));
        },
        function(error) {
            if (error.code === 1) {
                alert("Please allow location access for SOS to work!");
            } else if (error.code === 2) {
                alert("Location unavailable: make sure location is enabled on your device.");
            } else if (error.code === 3) {
                alert("Getting location timed out! Try again.");
            } else {
                alert("Geolocation error: " + error.message);
            }
        },
        {timeout: 15000}
    );
});
</script>

</body>
</html>
