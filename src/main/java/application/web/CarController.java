package application.web;

import application.model.Carcomponent;
import application.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    
    private final CarService carService = new CarService();

    @GetMapping
    public List<Carcomponent> getAllCars() {
        return carService.getAllCars();
    }

    @PostMapping
    public ResponseEntity<?> addCar(@RequestBody Map<String, String> payload) {
        boolean success = carService.addCar(
            payload.get("carId"),
            payload.get("brand"),
            payload.get("model"),
            payload.get("price"),
            payload.get("status")
        );
        if (success) return ResponseEntity.ok().body(Map.of("message", "Car added successfully"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Invalid or duplicate car details"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCar(@PathVariable String id, @RequestBody Map<String, String> payload) {
        boolean success = carService.updateCar(
            id,
            payload.get("brand"),
            payload.get("model"),
            payload.get("price"),
            payload.get("status")
        );
        if (success) return ResponseEntity.ok().body(Map.of("message", "Car updated successfully"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Invalid car details"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable String id) {
        boolean success = carService.deleteCar(id);
        if (success) return ResponseEntity.ok().body(Map.of("message", "Car deleted successfully"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Failed to delete car"));
    }
}
