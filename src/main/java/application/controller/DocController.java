package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import application.service.AuthService;

public class DocController implements Initializable {

    @FXML private Button close;
    @FXML private Button loginBtn;
    @FXML private PasswordField password;
    @FXML private TextField username;
    
    private AuthService authService = new AuthService();

    public void close() {
        System.exit(0);
    }
    
    public void loginAdmin() {
        String user = username.getText();
        String pass = password.getText();
        
        if (authService.login(user, pass)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Successful Login");
            alert.showAndWait();
            
            try {
                loginBtn.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("/application/mainMenu.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                application.util.ScreenshotUtil.attachScreenshotListener(scene);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Wrong username or password.");
            alert.showAndWait();
        }
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    }
}
