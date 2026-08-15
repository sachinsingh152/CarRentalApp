package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import application.util.ScreenshotUtil;

public class Main extends Application {
    @Override
    public void start (Stage stage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/application/FxmalDocumant.fxml"));
            Scene scene = new Scene(root);
            
            // Attach our headless screenshot utility
            ScreenshotUtil.attachScreenshotListener(scene);
            
            stage.setTitle("Car Rental Management System");
            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
