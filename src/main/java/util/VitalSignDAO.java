package util;

import com.example.seniorcare.model.VitalSign;
import util.DBConn;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VitalSignDAO {

    public List<VitalSign> getVitalsByUserId(int userId) throws SQLException {
        List<VitalSign> vitals = new ArrayList<>();
        String sql = "SELECT * FROM vital_signs WHERE user_id = ? ORDER BY timestamp DESC";
        try (Connection conn = DBConn.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                VitalSign vital = new VitalSign(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getTimestamp("timestamp").toLocalDateTime(),
                    rs.getInt("systolic"),
                    rs.getInt("diastolic"),
                    rs.getInt("heartrate"),
                    rs.getInt("bloodsugar"),
                    rs.getInt("spo2")
                );
                vitals.add(vital);
            }
        }
        return vitals;
    }

}
