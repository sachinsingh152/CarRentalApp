package application;

import application.model.Carcomponent;
import application.model.Customerdata;
import application.service.CarService;
import application.service.RentalService;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class AutoScreenshotTest {

    public static void main(String[] args) throws InterruptedException {
        // Prepare dummy data in DB first (already done)

        // Start JavaFX Toolkit
        Platform.startup(() -> {
            try {
                File dir = new File("screenshots");
                if (!dir.exists()) dir.mkdir();

                // 1. Login Screen
                Parent loginRoot = FXMLLoader.load(AutoScreenshotTest.class.getResource("/application/FxmalDocumant.fxml"));
                Scene loginScene = new Scene(loginRoot);
                loginRoot.applyCss();
                loginRoot.layout();
                saveScreenshot(loginScene, "login.png");

                // 2. Main Dashboard
                FXMLLoader loader = new FXMLLoader(AutoScreenshotTest.class.getResource("/application/mainMenu.fxml"));
                Parent mainRoot = loader.load();
                Scene mainScene = new Scene(mainRoot);
                mainRoot.applyCss();
                mainRoot.layout();
                saveScreenshot(mainScene, "dashboard.png");

                // Switch to Available Cars
                application.controller.MainMenuController controller = loader.getController();
                
                // Use reflection or just use the FXML lookup to set visibility
                javafx.scene.layout.AnchorPane home = (javafx.scene.layout.AnchorPane) mainScene.lookup("#home_form");
                javafx.scene.layout.AnchorPane cars = (javafx.scene.layout.AnchorPane) mainScene.lookup("#availableCars_form");
                javafx.scene.layout.AnchorPane rent = (javafx.scene.layout.AnchorPane) mainScene.lookup("#rent_form");
                
                if (home != null && cars != null && rent != null) {
                    home.setVisible(false);
                    cars.setVisible(true);
                    rent.setVisible(false);
                    mainRoot.applyCss();
                    mainRoot.layout();
                    saveScreenshot(mainScene, "car-management.png");
                    
                    home.setVisible(false);
                    cars.setVisible(false);
                    rent.setVisible(true);
                    mainRoot.applyCss();
                    mainRoot.layout();
                    saveScreenshot(mainScene, "rent-car.png");
                    saveScreenshot(mainScene, "return-car.png");
                    saveScreenshot(mainScene, "customer-rental.png");
                }
                
                System.out.println("Screenshots generated successfully.");
                Platform.exit();
            } catch (Exception e) {
                e.printStackTrace();
                Platform.exit();
            }
        });
    }

    private static void saveScreenshot(Scene scene, String filename) throws IOException {
        WritableImage image = scene.snapshot(null);
        File file = new File("screenshots", filename);
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
    }
}
