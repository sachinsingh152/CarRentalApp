package application.web;

import application.dao.CarDAO;
import application.dao.CustomerDAO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final CarDAO carDAO = new CarDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();

    @GetMapping
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalCars", carDAO.getAllCars().size());
        stats.put("availableCars", carDAO.getAvailableCarsCount());
        stats.put("totalCustomers", customerDAO.getTotalCustomersCount());
        stats.put("totalIncome", customerDAO.getTotalIncome());
        return stats;
    }
}
