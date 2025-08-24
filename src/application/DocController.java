package application;

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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DocController implements Initializable 
{

	
	  @FXML
	    private Button close;

	    @FXML
	    private Button loginBtn;

	    @FXML
	    private AnchorPane main_form;

	    @FXML
	    private PasswordField password;

	    @FXML
	    private TextField username;
	    
	  
	    
	    public void close() 
	    {
	    	System.exit(0);
		}
         public void loginAdmin() {
        	 String username1=username.getText();
        	 String password1=password.getText();
        	 Alert alert;
        	 if( Storedata.usernamev.contains(username1) && Storedata.passwordv.contains(password1) ) 
        	 {
        		 Storedata.username=username1;
        		 Storedata.password=password1;
        		 alert=new Alert(AlertType.INFORMATION);
        		 alert.setHeaderText(null);
        		 alert.setContentText("Successful Login");
        		 alert.showAndWait();
        		 try 
        		 {
        			 
        			loginBtn.getScene().getWindow().hide();
        			
       			    Parent root=FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
					Stage stage=new Stage();
					Scene scene=new Scene(root);
					stage.setScene(scene);
					stage.show();
				 } 
        		catch (IOException e) 
        		{
					e.printStackTrace();
				}
        		 
        	 }
        	 else {
        		 alert=new Alert(AlertType.ERROR);
        		 alert.setHeaderText(null);
        		 alert.setContentText("wrong username/password");
        		 alert.showAndWait();
        	 }
             
         }
	public void initialize(URL arg0, ResourceBundle arg1) 
	{

	}

}
