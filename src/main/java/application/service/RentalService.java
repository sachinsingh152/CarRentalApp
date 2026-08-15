package application.service;

import application.dao.CarDAO;
import application.dao.CustomerDAO;
import application.model.Customerdata;
import java.util.List;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class RentalService {
    private CarDAO carDAO = new CarDAO();
    private CustomerDAO customerDAO = new CustomerDAO();
    
    public List<Customerdata> getAllRentals() {
        return customerDAO.getAllCustomers();
    }
    
    public double calculateRentalPrice(String carId, LocalDate rentDate, LocalDate returnDate) {
        if (carId == null || rentDate == null || returnDate == null) return 0.0;
        
        long days = ChronoUnit.DAYS.between(rentDate, returnDate);
        if (days < 0) return -1.0; // Invalid date
        if (days == 0) days = 1; // Same day rental
        
        String priceStr = carDAO.getPrice(carId);
        if (priceStr == null) return 0.0;
        
        try {
            double pricePerDay = Double.parseDouble(priceStr);
            return pricePerDay * days;
        } catch (Exception e) {
            return 0.0;
        }
    }
    
    public boolean rentCar(String carId, String fname, String lname, String gender, LocalDate rentDate, LocalDate returnDate, double total) {
        if (carId == null || fname.isEmpty() || lname.isEmpty() || gender == null || total <= 0) {
            return false;
        }
        customerDAO.addCustomer(carId, fname, lname, gender, String.valueOf(total), rentDate.toString(), returnDate.toString());
        carDAO.updateCarStatus(carId, "--Not Available--");
        return true;
    }
    
    public boolean returnCar(String carId) {
        if (carId == null || carId.isEmpty()) return false;
        
        carDAO.updateCarStatus(carId, "--Available--");
        customerDAO.updateCustomerStatus(carId, "return");
        return true;
    }
}
