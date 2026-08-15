package application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import application.config.DatabaseConfig;

public class AdminDAO {
    public String getPasswordHash(String username) {
        String query = "SELECT password FROM admin WHERE username = ?";
        try (Connection con = DatabaseConfig.connectDb();
             PreparedStatement pr = con != null ? con.prepareStatement(query) : null) {
            
            if (pr == null) return null;
            pr.setString(1, username);
            try (ResultSet rs = pr.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("password");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
