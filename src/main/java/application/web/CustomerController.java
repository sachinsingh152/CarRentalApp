package application.web;

import application.model.Customerdata;
import application.service.RentalService;
import application.dao.CustomerDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CustomerController {
    
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final RentalService rentalService = new RentalService();

    @GetMapping("/customers")
    public List<Customerdata> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    @PostMapping("/rentals")
    public ResponseEntity<?> rentCar(@RequestBody Map<String, String> payload) {
        try {
            String carId = payload.get("carId");
            String firstName = payload.get("firstName");
            String lastName = payload.get("lastName");
            String gender = payload.get("gender");
            LocalDate rentDate = LocalDate.parse(payload.get("rentDate"));
            LocalDate returnDate = LocalDate.parse(payload.get("returnDate"));
            
            // Check availability first conceptually handled in RentalService and CarService,
            // but for simplicity we rely on the service check
            if (carId == null || carId.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Missing car selection"));
            }

            Double price = rentalService.calculateRentalPrice(carId, rentDate, returnDate);
            if (price != null && price > 0) {
                rentalService.rentCar(carId, firstName, lastName, gender, rentDate, returnDate, price);
                return ResponseEntity.ok().body(Map.of("message", "Car rented successfully", "price", price));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Invalid dates or car unavailable"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/rentals/{carId}/return")
    public ResponseEntity<?> returnCar(@PathVariable String carId) {
        if (carId == null || carId.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid car ID"));
        }
        rentalService.returnCar(carId);
        return ResponseEntity.ok().body(Map.of("message", "Car returned successfully"));
    }
}
