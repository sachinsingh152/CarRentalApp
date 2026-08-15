package application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import application.config.DatabaseConfig;
import application.model.Carcomponent;

public class CarDAO {

    public List<Carcomponent> getAllCars() {
        List<Carcomponent> list = new ArrayList<>();
        String query = "SELECT * FROM car";
        try (Connection con = DatabaseConfig.connectDb();
             PreparedStatement pr = con != null ? con.prepareStatement(query) : null;
             ResultSet rs = pr != null ? pr.executeQuery() : null) {
             
            if (rs == null) return list;
            while (rs.next()) {
                list.add(new Carcomponent(
                    rs.getString("car_id"), 
                    rs.getString("brand"),
                    rs.getString("model"), 
                    rs.getString("price"),
                    rs.getString("status"),
                    rs.getDate("date")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addCar(String car_id, String brand, String model, String price, String status) {
        String query = "INSERT INTO car(car_id,brand,model,price,status) VALUES (?,?,?,?,?)";
        try (Connection con = DatabaseConfig.connectDb();
             PreparedStatement pr = con != null ? con.prepareStatement(query) : null) {
            
            if (pr == null) return;
            pr.setString(1, car_id);
            pr.setString(2, brand);
            pr.setString(3, model);
            pr.setString(4, price);
            pr.setString(5, status);
            pr.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void updateCar(String car_id, String brand, String model, String price, String status) {
        String query = "UPDATE car SET brand=?, model=?, status=?, price=? WHERE car_id=?";
        try (Connection con = DatabaseConfig.connectDb();
             PreparedStatement pr = con != null ? con.prepareStatement(query) : null) {
            
            if (pr == null) return;
            pr.setString(1, brand);
            pr.setString(2, model);
            pr.setString(3, status);
            pr.setString(4, price);
            pr.setString(5, car_id);
            pr.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCar(String car_id) {
        String query = "DELETE FROM car WHERE car_id=?";
        try (Connection con = DatabaseConfig.connectDb();
             PreparedStatement pr = con != null ? con.prepareStatement(query) : null) {
            
            if (pr == null) return;
            pr.setString(1, car_id);
            pr.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPrice(String carId) {
        String query = "SELECT price FROM car WHERE car_id=?";
        try (Connection con = DatabaseConfig.connectDb();
             PreparedStatement pr = con != null ? con.prepareStatement(query) : null) {
            
            if (pr == null) return null;
            pr.setString(1, carId);
            try (ResultSet rs = pr.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("price");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateCarStatus(String carId, String status) {
        String query = "UPDATE car SET status=? WHERE car_id=?";
        try (Connection con = DatabaseConfig.connectDb();
             PreparedStatement pr = con != null ? con.prepareStatement(query) : null) {
            
            if (pr == null) return;
            pr.setString(1, status);
            pr.setString(2, carId);
            pr.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int getAvailableCarsCount() {
        String query = "SELECT COUNT(*) AS count FROM car WHERE status='--Available--'";
        try (Connection con = DatabaseConfig.connectDb();
             PreparedStatement pr = con != null ? con.prepareStatement(query) : null;
             ResultSet rs = pr != null ? pr.executeQuery() : null) {
             
            if (rs != null && rs.next()) {
                return rs.getInt("count");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
