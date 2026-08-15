package application.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import application.service.AuthService;
import application.service.DashboardService;
import application.service.CarService;
import application.service.RentalService;
import application.model.Carcomponent;
import application.model.Customerdata;

public class MainMenuController implements Initializable {
    
    // Services
    private DashboardService dashboardService = new DashboardService();
    private CarService carService = new CarService();
    private RentalService rentalService = new RentalService();
    private AuthService authService = new AuthService();

    // FXML Bindings - Car Management
    @FXML private TextField a_brand;
    @FXML private TextField a_carId;
    @FXML private Button a_clearBtn;
    @FXML private Button a_deleteBtn;
    @FXML private Button a_insertBtn;
    @FXML private TextField a_model;
    @FXML private TextField a_price;
    @FXML private ComboBox<String> a_status;
    @FXML private TableView<Carcomponent> a_tableView;
    @FXML private Button a_updateBtn;
    @FXML private TableColumn<Carcomponent,String> ac_brand;
    @FXML private TableColumn<Carcomponent,String> ac_carId;
    @FXML private TableColumn<Carcomponent,String> ac_model;
    @FXML private TableColumn<Carcomponent,String> ac_price;
    @FXML private TableColumn<Carcomponent,String> ac_status;

    // FXML Bindings - Nav & Views
    @FXML private Button availableCars_btn;
    @FXML private AnchorPane availableCars_form;
    @FXML private Button close;
    @FXML private AnchorPane h_availableCars;
    @FXML private AnchorPane h_totalIncome;
    @FXML private Button home_btn;
    @FXML private AnchorPane home_form;
    @FXML private Button logoutBtn;
    @FXML private AnchorPane main_form;

    // FXML Bindings - Dashboard
    @FXML private Label availcar;
    @FXML private Label Totalp;
    @FXML private Label totcust;
    @FXML private Label username;

    // FXML Bindings - Rental & Return
    @FXML private ComboBox<String> r_carId;
    @FXML private DatePicker r_dateRented;
    @FXML private DatePicker r_dateReturn;
    @FXML private TextField r_firstName;
    @FXML private ComboBox<String> r_gender;
    @FXML private TextField r_lastName;
    @FXML private Label r_total;
    @FXML private Button rentBtn;
    @FXML private Button rentCar_btn;
    @FXML private AnchorPane rent_form;
    @FXML private TableView<Customerdata> rent_tableView;
    @FXML private TableColumn<Customerdata, String> rc_carId;
    @FXML private TableColumn<Customerdata, String> rc_dateRented;
    @FXML private TableColumn<Customerdata, String> rc_dateReturn;
    @FXML private TableColumn<Customerdata, String> rc_fname;
    @FXML private TableColumn<Customerdata, String> rc_lname;
    @FXML private TableColumn<Customerdata, String> rc_price;
    @FXML private TableColumn<Customerdata, String> rc_status;
    @FXML private TextField returnid;

    public void returnfn() {
        if (returnid.getText() == null || returnid.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Error Message", "Please enter a Car ID to return");
            return;
        }
        boolean success = rentalService.returnCar(returnid.getText());
        if (success) {
            showAlert(AlertType.INFORMATION, "Message", "Car successfully returned.");
            displaycustomer();
            dashboardData();
            returnid.setText("");
        } else {
            showAlert(AlertType.ERROR, "Error Message", "Car ID not found or could not be returned.");
        }
    }

    public void rentfn() {
        double total = rdisrent();
        if (total <= 0 || r_firstName.getText().isEmpty() || r_lastName.getText().isEmpty() || r_gender.getSelectionModel().getSelectedItem() == null) {
            showAlert(AlertType.ERROR, "Error Message", "Please fill all blank fields with valid data.");
            return;
        }
        
        boolean success = rentalService.rentCar(
            r_carId.getSelectionModel().getSelectedItem(),
            r_firstName.getText(),
            r_lastName.getText(),
            r_gender.getSelectionModel().getSelectedItem(),
            r_dateRented.getValue(),
            r_dateReturn.getValue(),
            total
        );
        
        if (success) {
            showAlert(AlertType.INFORMATION, "Message", "Successfully Added");
            displaycustomer();
            rtable();
            dashboardData();
        } else {
            showAlert(AlertType.ERROR, "Error Message", "Failed to rent car.");
        }
    }

    public void displaycustomer() {
        ObservableList<Customerdata> acarlist = rentalService.getAllRentals();
        
        rc_carId.setCellValueFactory(new PropertyValueFactory<>("carId"));
        rc_fname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        rc_lname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        rc_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        rc_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        rc_dateRented.setCellValueFactory(new PropertyValueFactory<>("dateRetened"));
        rc_dateReturn.setCellValueFactory(new PropertyValueFactory<>("dateReturn"));

        rent_tableView.setItems(acarlist);
    }
    
    public double rdisrent() {
        if (r_carId.getSelectionModel().getSelectedItem() == null || r_dateRented.getValue() == null || r_dateReturn.getValue() == null) {
            r_total.setText("₹ 0.0");
            return 0;
        }
        double total = rentalService.calculateRentalPrice(
            r_carId.getSelectionModel().getSelectedItem(),
            r_dateRented.getValue(),
            r_dateReturn.getValue()
        );
        if (total < 0) {
            showAlert(AlertType.ERROR, "Error", "Return date cannot be before rent date.");
            total = 0;
        }
        r_total.setText("₹ " + String.valueOf(total));
        return total;
    }

    public void rtable() {
        ObservableList<Carcomponent> carlist = carService.getAllCars();
        r_carId.getItems().clear();
        for (Carcomponent car : carlist) {
            if ("--Available--".equals(car.getStatus())) {
                r_carId.getItems().add(car.getCarId());
            }
        }
    }

    public void rgender() {
        r_gender.getItems().addAll("Male", "Female");
    }

    public void addcar() {
        if (a_carId.getText().isEmpty() || a_brand.getText().isEmpty() || a_model.getText().isEmpty() || a_status.getSelectionModel().getSelectedItem() == null || a_price.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Error Message", "Please fill all blank fields");
        } else {
            boolean success = carService.addCar(a_carId.getText(), a_brand.getText(), a_model.getText(), a_price.getText(), a_status.getSelectionModel().getSelectedItem());
            if (success) {
                showAlert(AlertType.INFORMATION, "Message", "Successfully Added");
                displaycar();
                dashboardData();
            } else {
                showAlert(AlertType.ERROR, "Error Message", "Invalid price or car ID already exists.");
            }
        }
    }

    public void aCarList() {
        List<String> li = new ArrayList<>();
        li.add("--Available--");
        li.add("--Not Available--");
        a_status.setItems(FXCollections.observableArrayList(li));
    }

    public void displaycar() {
        ObservableList<Carcomponent> acarlist = carService.getAllCars();
        ac_carId.setCellValueFactory(new PropertyValueFactory<>("carId"));
        ac_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        ac_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        ac_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        ac_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        a_tableView.setItems(acarlist);
    }

    public void dUsername() {
        String sessionUser = AuthService.currentUsername;
        username.setText(sessionUser != null ? sessionUser : "Admin");
    }

    public void logout() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Logout Confirmation");
        alert.setHeaderText("You are about to log out.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            authService.logout();
            logoutBtn.getScene().getWindow().hide();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/application/FxmalDocumant.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                application.util.ScreenshotUtil.attachScreenshotListener(scene);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void aClear() {
        a_carId.setText("");
        a_brand.setText("");
        a_model.setText("");
        a_status.getSelectionModel().clearSelection();
        a_price.setText("");
    }

    public void aDelete() {
        if (a_carId.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Error Message", "Please select or enter a Car ID to delete");
        } else {
            carService.deleteCar(a_carId.getText());
            showAlert(AlertType.INFORMATION, "Message", "Successfully Deleted");
            displaycar();
            dashboardData();
        }
    }

    public void aUpdate() {
        if (a_carId.getText().isEmpty() || a_brand.getText().isEmpty() || a_model.getText().isEmpty() || a_status.getSelectionModel().getSelectedItem() == null || a_price.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Error Message", "Please fill all blank fields");
        } else {
            boolean success = carService.updateCar(a_carId.getText(), a_brand.getText(), a_model.getText(), a_price.getText(), a_status.getSelectionModel().getSelectedItem());
            if (success) {
                showAlert(AlertType.INFORMATION, "Message", "Successfully Updated");
                displaycar();
                dashboardData();
            } else {
                showAlert(AlertType.ERROR, "Error Message", "Invalid price format.");
            }
        }
    }

    public void close() {
        System.exit(0);
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == home_btn) {
            home_form.setVisible(true);
            availableCars_form.setVisible(false);
            rent_form.setVisible(false);
            dashboardData();
        } else if (event.getSource() == availableCars_btn) {
            home_form.setVisible(false);
            availableCars_form.setVisible(true);
            rent_form.setVisible(false);
            displaycar();
        } else if (event.getSource() == rentCar_btn) {
            home_form.setVisible(false);
            availableCars_form.setVisible(false);
            rent_form.setVisible(true);
            rtable();
            displaycustomer();
        }
    }

    private void dashboardData() {
        availcar.setText(String.valueOf(dashboardService.getAvailableCarsCount()));
        totcust.setText(String.valueOf(dashboardService.getTotalCustomersCount()));
        Totalp.setText("₹ " + dashboardService.getTotalIncome());
    }

    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        dUsername();
        displaycar();
        aCarList();
        rgender();
        displaycustomer();
        rtable();
        dashboardData();
    }
}
