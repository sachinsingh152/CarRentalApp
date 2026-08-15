package application.service;

import application.dao.CarDAO;
import application.dao.CustomerDAO;

public class DashboardService {
    private CarDAO carDAO = new CarDAO();
    private CustomerDAO customerDAO = new CustomerDAO();
    
    public int getAvailableCarsCount() {
        return carDAO.getAvailableCarsCount();
    }
    
    public int getTotalCustomersCount() {
        return customerDAO.getTotalCustomersCount();
    }
    
    public double getTotalIncome() {
        return customerDAO.getTotalIncome();
    }
}
