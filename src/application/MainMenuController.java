package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MainMenuController implements Initializable
{
    
	   @FXML
	    private TextField a_brand;

	    @FXML
	    private TextField a_carId;

	    @FXML
	    private Button a_clearBtn;

	    @FXML
	    private Button a_deleteBtn;

	    @FXML
	    private ImageView a_imageView;

	    @FXML
	    private Button a_importBtn;

	    @FXML
	    private Button a_insertBtn;

	    @FXML
	    private TextField a_model;

	    @FXML
	    private TextField a_price;

	    @FXML
	    private TextField a_search;

	    @FXML
	    private ComboBox<?> a_status;

	    @FXML
	    private TableView<Carcomponent> a_tableView;

	    @FXML
	    private Button a_updateBtn;

	    @FXML
	    private TableColumn<Carcomponent,String> ac_brand;

	    @FXML
	    private TableColumn<Carcomponent,String> ac_carId;

	    @FXML
	    private TableColumn<Carcomponent,String> ac_model;

	    @FXML
	    private TableColumn<Carcomponent,String> ac_price;

	    @FXML
	    private TableColumn<Carcomponent,String> ac_status;

	    @FXML
	    private Button availableCars_btn;

	    @FXML
	    private AnchorPane availableCars_form;

	    @FXML
	    private Button close;

	    @FXML
	    private Label h_TotalCustomers;

	    @FXML
	    private AnchorPane h_availableCars;

	    @FXML
	    private LineChart<?, ?> h_customerChart;

	    @FXML
	    private BarChart<?, ?> h_incomeChart;

	    @FXML
	    private AnchorPane h_totalIncome;

	    @FXML
	    private Button home_btn;

	    @FXML
	    private AnchorPane home_form;

	    @FXML
	    private Button logoutBtn;

	    @FXML
	    private AnchorPane main_form;

	    @FXML
	    private TextField r_amount;

	    @FXML
	    private Label r_balance;

	   

	    @FXML
	    private ComboBox<String> r_carId;

	    @FXML
	    private DatePicker r_dateRented;

	    @FXML
	    private DatePicker r_dateReturn;

	    @FXML
	    private TextField r_firstName;

	    @FXML
	    private ComboBox<String> r_gender;

	    @FXML
	    private TextField r_lastName;

	   

	    @FXML
	    private Label r_total;
        
	    @FXML
	    private TableColumn<?, ?> rc_carId;

	    @FXML
	    private TableColumn<?, ?> rc_dateRented;

	    @FXML
	    private TableColumn<?, ?> rc_dateReturn;

	    @FXML
	    private TableColumn<?, ?> rc_fname;

	    @FXML
	    private TableColumn<?, ?> rc_lname;

	    @FXML
	    private TableColumn<?, ?> rc_price;

	    @FXML
	    private TableColumn<?, ?> rc_status;

	 

	    @FXML
	    private Button rentBtn;

	    @FXML
	    private Button rentCar_btn;

	    @FXML
	    private AnchorPane rent_form;

	    @FXML
	    private TableView<Customerdata> rent_tableView;

	    @FXML
	    private Label username;
	    
	    @FXML
	    private TextField returnid;
	    
	    @FXML
	    private Label availcar;
	    
	    @FXML
	    private Label Totalp;
	    
	    @FXML
	    private Label totcust;
	    
	    public void returnfn() {
	    	Alert alert;
	    	if(returnid.getText()==null) {
	    		    alert = new Alert(AlertType.ERROR);
	                alert.setTitle("Error Message");
	                alert.setHeaderText(null);
	                alert.setContentText("Please fill all blank fields");
	                alert.showAndWait();
	    	}
	    	else {
	    		Storedata.updatestatus1(returnid.getText());
	    		Storedata.updatestatus2(returnid.getText());
	    		displaycustomer();
	    	}
	    }
	    
	    
	    public void rentfn() {
	    	Alert alert;
	    	double total=rdisrent();
	    	if(total==0
	    	  || r_firstName.getText().isEmpty()
	          || r_lastName.getText().isEmpty()
	          || r_gender.getSelectionModel().getSelectedItem() == null
	    			) {
	    		   alert = new Alert(AlertType.ERROR);
	                alert.setTitle("Error Message");
	                alert.setHeaderText(null);
	                alert.setContentText("Please fill all blank fields");
	                alert.showAndWait();	
	    	}
	    	else {
	    		 Storedata.addcust(r_carId.getSelectionModel().getSelectedItem(),r_firstName.getText() , r_lastName.getText(), r_gender.getSelectionModel().getSelectedItem(), String.valueOf(total), r_dateRented.getValue().toString(), r_dateReturn.getValue().toString());
	    		 Storedata.updatestatus(r_carId.getSelectionModel().getSelectedItem()); 
	    		 alert = new Alert(AlertType.INFORMATION);
	             alert.setTitle("Message");
	             alert.setHeaderText(null);
	             alert.setContentText("Succesfully Added");
	             alert.showAndWait();
	             Storedata.updatestatus(r_carId.getSelectionModel().getSelectedItem());
	             displaycustomer();
	    	}
	    }
	    
	     
	    
	    public void displaycustomer() {
	    	    ObservableList<Customerdata> acarlist = Storedata.getcust();
                int count=acarlist.size();
                totcust.setText(String.valueOf(count));
                double price=0;
                for(int i=0;i<acarlist.size();i++) {
                	Customerdata temp=acarlist.get(i);
                	price+= Double.parseDouble(temp.getPrice());
                }
                Totalp.setText("₹ "+String.valueOf(price)+" /-");
		        rc_carId.setCellValueFactory(new PropertyValueFactory<>("carId"));
		        rc_fname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		        rc_lname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		        rc_price.setCellValueFactory(new PropertyValueFactory<>("price"));
		        rc_status.setCellValueFactory(new PropertyValueFactory<>("status"));
		        rc_dateRented.setCellValueFactory(new PropertyValueFactory<>("dateRetened"));
		        rc_dateReturn.setCellValueFactory(new PropertyValueFactory<>("dateReturn"));
		        

		        rent_tableView.setItems(acarlist);
	    }
	    
	    
	    private int tday=0;
	    public void rcaltday() {
	    	Alert alert;
	    	
	    	if( r_carId.getSelectionModel().getSelectedItem()==null 
	    			|| r_dateRented.getValue()==null
	    			|| r_dateReturn.getValue()==null
	    			) {
	    		    alert = new Alert(AlertType.ERROR);
	                alert.setTitle("Error Message");
	                alert.setHeaderText(null);
	                alert.setContentText("Please fill all blank fields");
	                alert.showAndWait();
	    	}else {
	    		  LocalDate rentedDate = r_dateRented.getValue();
		          LocalDate returnDate = r_dateReturn.getValue();
	    	     if(rentedDate.equals(returnDate))	{
	    	    	 tday=1;
	    	     }
	    	     else {
	    	    	 tday = (int) ChronoUnit.DAYS.between(rentedDate, returnDate);
	    	     }
	    	}
	    		
	    }
	    
	    
	    public double rdisrent() {
	    	double total=0;
	    	rcaltday();
	    	String x=Storedata.getPricee(r_carId.getSelectionModel().getSelectedItem());
	    	if(x==null) {
	    		return 0;
	    	}
	    	double number = Double.parseDouble(x);
	        total=number*tday;
	    	r_total.setText(String.valueOf(total));
	    	return total;
	    }
	    
	    
	    public void rtable() {
	    	ObservableList<Carcomponent> carlist=Storedata.getcar();
	    	r_carId.getItems().clear();
	    	int y=0;
	    	for(int i=0;i<carlist.size();i++) {
	    		Carcomponent temp=carlist.get(i);
	    		if(temp.getStatus().equals("--Available--")) {
	    		    y++;
	    		    
	    		     r_carId.getItems().add(temp.getCarId());
	    		}
	    		
	    		
	    	}
	    	
	    	availcar.setText(String.valueOf(y));
           
		        
	    }
	    
	    public void rgender() {
	    	r_gender.getItems().add("Male");
	    	r_gender.getItems().add("Female");
	    }
	    
	    
	    
	    
	    
	    public void addcar() {
	    	Alert alert;
            if (a_carId.getText().isEmpty()
                    || a_brand.getText().isEmpty()
                    || a_model.getText().isEmpty()
                    || a_status.getSelectionModel().getSelectedItem() == null
                    || a_price.getText().isEmpty()
                    ) 
            {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
	    	Storedata.addcard((a_carId.getText()), a_brand.getText(),a_model.getText() , a_price.getText() , (String)a_status.getSelectionModel().getSelectedItem());
	    	 alert = new Alert(AlertType.INFORMATION);
             alert.setTitle("Message");
             alert.setHeaderText(null);
             alert.setContentText("Succesfully Added");
             alert.showAndWait();
             displaycar();
            }
	    }
	    
	    public void aCarList() 
	    {
	    	List<String> li = new ArrayList<>();

	    	li.add("--Available--");
	    	li.add("--Not Available--");
	    	
	    	ObservableList liData = FXCollections.observableArrayList(li);
	    	a_status.setItems(liData);
		}
	    
	    
	    public void displaycar() {
	    	    ObservableList<Carcomponent> acarlist = Storedata.getcar();

		        ac_carId.setCellValueFactory(new PropertyValueFactory<>("carId"));
		        ac_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
		        ac_model.setCellValueFactory(new PropertyValueFactory<>("model"));
		        ac_price.setCellValueFactory(new PropertyValueFactory<>("price"));
		        ac_status.setCellValueFactory(new PropertyValueFactory<>("status"));

		        a_tableView.setItems(acarlist);
	    }
	    
	    
	    
	    public void dUsername() {
	    	String username1=Storedata.username;
	    	username.setText(username1);
	    }
	    
	    public void logout() {
	    	 Alert alert = new Alert(AlertType.CONFIRMATION);
	         alert.setTitle("Logout Confirmation");
	         alert.setHeaderText("You are about to log out.");
	      
	         
	         
	         Optional<ButtonType> result = alert.showAndWait();
	         
	         if (result.isPresent() && result.get() == ButtonType.OK) {
                  
	        	  logoutBtn.getScene().getWindow().hide();  
	        	  Parent root = null;
				try {
					 root = FXMLLoader.load(getClass().getResource("FxmalDocumant.fxml"));
				} catch (IOException e) {
					e.printStackTrace();
				}
					Stage stage=new Stage();
					Scene scene=new Scene(root);
					stage.setScene(scene);
					stage.show();
	         } 
	    }
	    
	    public void aClear()
	    {
	        a_carId.setText("");
	        a_brand.setText("");
	        a_model.setText("");
	        a_status.getSelectionModel().clearSelection();
	        a_price.setText("");

		}
	    
	    
	    public void aDelete() 
	    {
	      	Alert alert;
            if (a_carId.getText().isEmpty()
                    ) 
            {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else {
            	Storedata.deletecard(a_carId.getText());
            	 alert = new Alert(AlertType.INFORMATION);
                 alert.setTitle("Message");
                 alert.setHeaderText(null);
                 alert.setContentText("Succesfully Deleted");
                 alert.showAndWait();
                 displaycar();
            }
		}
	    
	    public void aUpdate() 
	    {
	     	Alert alert;
            if (a_carId.getText().isEmpty()
                    || a_brand.getText().isEmpty()
                    || a_model.getText().isEmpty()
                    || a_status.getSelectionModel().getSelectedItem() == null
                    || a_price.getText().isEmpty()
                    ) 
            {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else {
	    	Storedata.updatecard((a_carId.getText()), a_brand.getText(),a_model.getText() , a_price.getText() , (String)a_status.getSelectionModel().getSelectedItem());
            
	    	 alert = new Alert(AlertType.INFORMATION);
             alert.setTitle("Message");
             alert.setHeaderText(null);
             alert.setContentText("Succesfully Updated");
             alert.showAndWait();
             displaycar();
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
	    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		dUsername();
		displaycar();
		aCarList();
		rgender();
		displaycustomer();
		rtable();
	}

}
