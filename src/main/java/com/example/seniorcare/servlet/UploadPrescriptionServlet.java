package com.example.seniorcare.servlet;

import util.DBConn;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.nio.file.*;
import java.sql.*;

@WebServlet("/UploadPrescriptionServlet")
@MultipartConfig(
    fileSizeThreshold = 2 * 1024 * 1024,
    maxFileSize = 10 * 1024 * 1024,
    maxRequestSize = 50 * 1024 * 1024)
public class UploadPrescriptionServlet extends HttpServlet {
    private static final String UPLOAD_DIR = "uploads";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        int userId = (int) session.getAttribute("userId");

        String presName = request.getParameter("name");
        Part filePart = request.getPart("prescriptionFile");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

        // Save file inside webapp/uploads folder
        String appPath = request.getServletContext().getRealPath("");
        String uploadPath = appPath + File.separator + UPLOAD_DIR;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
        String relativeFilePath = UPLOAD_DIR + "/" + uniqueFileName;

        File fileToSave = new File(uploadPath, uniqueFileName);
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            request.setAttribute("message", "File upload failed: " + e.getMessage());
            request.getRequestDispatcher("medicalRecords.jsp").forward(request, response);
            return;
        }

        System.out.println("File saved at: " + fileToSave.getAbsolutePath());

        try (Connection conn = DBConn.getConnection()) {
            String sql = "INSERT INTO prescriptions (user_id, file_path, name) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, userId);
                ps.setString(2, relativeFilePath);
                ps.setString(3, presName);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            request.setAttribute("message", "Database error: " + e.getMessage());
            request.getRequestDispatcher("medicalRecords.jsp").forward(request, response);
            return;
        }

        response.sendRedirect(request.getContextPath() + "/MedicalRecordsListServlet");
    }
}
