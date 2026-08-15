package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarRentalWebApplication {
    public static void main(String[] args) {
        // Required so that JavaFX and Spring Boot don't conflict on headless property
        System.setProperty("java.awt.headless", "false");
        SpringApplication.run(CarRentalWebApplication.class, args);
    }
}
