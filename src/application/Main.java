package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start (Stage stage) throws Exception  
	{
		try 
		{
			Parent root = FXMLLoader.load(getClass().getResource("FxmalDocumant.fxml"));
			
			Scene scene = new Scene(root);
			
			
			stage.setScene(scene);
			
			
			stage.show();
		} catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) 
	{
		Storedata.getData();
		Storedata.getcar();
		launch("King");
	}
}
