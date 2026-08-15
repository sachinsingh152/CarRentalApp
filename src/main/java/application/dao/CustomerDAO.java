package application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import application.config.DatabaseConfig;
import application.model.Customerdata;

public class CustomerDAO {

    public void addCustomer(String car_id, String fname, String lname, String gend, String price, String drent, String dreturn) {
        String query = "INSERT INTO customer (car_id, firstname, lastname, gender, total, date_rent, date_return) VALUES (?,?,?,?,?,?,?)";
        try (Connection con = DatabaseConfig.connectDb();
             PreparedStatement pr = con != null ? con.prepareStatement(query) : null) {
            
            if (pr == null) return;
            pr.setString(1, car_id);
            pr.setString(2, fname);
            pr.setString(3, lname);
            pr.setString(4, gend);
            pr.setString(5, price);
            pr.setString(6, drent);
            pr.setString(7, dreturn);
            pr.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Customerdata> getAllCustomers() {
        ObservableList<Customerdata> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM customer";
        try (Connection con = DatabaseConfig.connectDb();
             PreparedStatement pr = con != null ? con.prepareStatement(query) : null;
             ResultSet rs = pr != null ? pr.executeQuery() : null) {
             
            if (rs == null) return list;
            while (rs.next()) {
                list.add(new Customerdata(
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    rs.getString("gender"),
                    rs.getString("car_id"),
                    rs.getString("total"),
                    rs.getString("status"),
                    rs.getString("date_rent"),
                    rs.getString("date_return")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateCustomerStatus(String carId, String status) {
        String query = "UPDATE customer SET status=? WHERE car_id=?";
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

    public int getTotalCustomersCount() {
        String query = "SELECT COUNT(*) AS count FROM customer";
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
    
    public double getTotalIncome() {
        String query = "SELECT SUM(total) AS total_income FROM customer";
        try (Connection con = DatabaseConfig.connectDb();
             PreparedStatement pr = con != null ? con.prepareStatement(query) : null;
             ResultSet rs = pr != null ? pr.executeQuery() : null) {
             
            if (rs != null && rs.next()) {
                return rs.getDouble("total_income");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
