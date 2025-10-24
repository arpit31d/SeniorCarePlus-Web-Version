<%@ page session="true" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.seniorcare.model.Prescription" %>
<%
    if (session.getAttribute("userId") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    List<Prescription> prescriptions = (List<Prescription>) request.getAttribute("prescriptions");
%>
<!DOCTYPE html>
<html>
<head>
  <title>Medical Records</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body class="bg-light">
<div class="container mt-4" style="max-width: 700px;">
  <h2>Upload Prescription</h2>
  <form method="post" action="${pageContext.request.contextPath}/UploadPrescriptionServlet" enctype="multipart/form-data">
    <div class="mb-3">
      <input type="text" name="name" class="form-control" placeholder="Prescription Name" required />
    </div>
    <div class="mb-3">
      <input type="file" name="prescriptionFile" accept="image/*" class="form-control" required />
    </div>
    <button type="submit" class="btn btn-primary">Upload</button>
  </form>

  <hr/>
  <h3>Uploaded Prescriptions</h3>

  <table class="table">
    <thead>
      <tr>
        <th>Name</th>
        <th>Preview</th>
        <th>Upload Date & Time</th>
        <th>Action</th>
      </tr>
    </thead>
    <tbody>
      <%
        if (prescriptions != null && !prescriptions.isEmpty()) {
            for (Prescription pres : prescriptions) {
      %>
      <tr>
        <td><%= pres.getName() %></td>
        <td>
          <img src="<%= request.getContextPath() + "/" + pres.getFilePath() %>"
               alt="Prescription" style="width:120px;height:auto;"/>
        </td>
        <td><%= pres.getUploadTime() %></td>
        <td>
          <a class="btn btn-sm btn-primary"
             href="<%= request.getContextPath() + "/" + pres.getFilePath() %>"
             target="_blank">Preview</a>
        </td>
      </tr>
      <%
            }
        } else {
      %>
      <tr>
        <td colspan="4">No prescriptions uploaded.</td>
      </tr>
      <%
        }
      %>
    </tbody>
  </table>

  <a href="index.jsp" class="btn btn-secondary">Back</a>
</div>
</body>
</html>
