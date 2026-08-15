package application.service;

import application.dao.CarDAO;
import application.model.Carcomponent;
import javafx.collections.ObservableList;

public class CarService {
    private CarDAO carDAO = new CarDAO();
    
    public ObservableList<Carcomponent> getAllCars() {
        return carDAO.getAllCars();
    }
    
    public boolean addCar(String carId, String brand, String model, String price, String status) {
        if (carId.isEmpty() || brand.isEmpty() || model.isEmpty() || price.isEmpty() || status == null) {
            return false;
        }
        try {
            Double.parseDouble(price); // Validate numeric
            carDAO.addCar(carId, brand, model, price, status);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public boolean updateCar(String carId, String brand, String model, String price, String status) {
        if (carId.isEmpty() || brand.isEmpty() || model.isEmpty() || price.isEmpty() || status == null) {
            return false;
        }
        try {
            Double.parseDouble(price);
            carDAO.updateCar(carId, brand, model, price, status);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public boolean deleteCar(String carId) {
        if (carId.isEmpty()) return false;
        carDAO.deleteCar(carId);
        return true;
    }
}
