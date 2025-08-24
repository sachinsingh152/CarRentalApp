package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Storedata 
{       
	
	    public static String username;
	    public static String password;
	    
	    private static Connection con;
	    private static PreparedStatement pr;
	    private static ResultSet rs;
	    static Vector<String> usernamev = new Vector<>();
		static  Vector<String> passwordv = new Vector<>();
		static void getData() 
		{
	    String query="select * from admin;";
	    con = Database.connectDb();
	    
	    try 
	    {
	    	pr=con.prepareStatement(query);
	    	rs=pr.executeQuery();
	    	
	    	while(rs.next()) 
	    	{
	    		usernamev.add(rs.getString("username"));
	    		passwordv.add(rs.getString("password")); 
	    	}
	    }
	    catch(Exception e) 
	    {
	    	e.printStackTrace();
	    }
	}
	public static ObservableList<Carcomponent> getcar(){
		ObservableList<Carcomponent> lcar=FXCollections.observableArrayList();
		String query="select * from car;";
		 con = Database.connectDb();
		 try {
			 pr=con.prepareStatement(query);
			 rs=pr.executeQuery();
			 Carcomponent cd;
			 while(rs.next()) {
				 cd = new Carcomponent(rs.getString("car_id"), rs.getString("brand"),
                         rs.getString("model"), rs.getString("price"),
                         rs.getString("status"),
                         rs.getDate("date"));

                lcar.add(cd);
			 }
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		 return lcar;
	}
	public static void addcard(String car_id,String brand,String model,String price,String status) {
		String query="insert into car(car_id,brand,model,price,status)  values (?,?,?,?,?);";
		con=Database.connectDb();
		try {
			
			pr=con.prepareStatement(query);
			pr.setString(1, car_id);
			pr.setString(2, brand);
			pr.setString(3, model);
			pr.setString(4, price);
			pr.setString(5, status);
			pr.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void updatecard(String car_id,String brand,String model,String price,String status) {
		String query="update car set brand=?,model=?,status=?,price=? where car_id=?;";
		con=Database.connectDb();
		try {
			
			pr=con.prepareStatement(query);
			pr.setString(5, car_id);
			pr.setString(1, brand);
			pr.setString(2, model);
			pr.setString(4, price);
			pr.setString(3, status);
			pr.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void deletecard(String car_id) {
		String query="Delete from car where car_id=?;";
		con=Database.connectDb();
		try {
			
			pr=con.prepareStatement(query);
			pr.setString(1, car_id);
			
			pr.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public static String getPricee(String carid) {
		String query="select price from car where car_id=?;";
		con=Database.connectDb();
		String y=null;
	try {
			
			pr=con.prepareStatement(query);
			pr.setString(1, carid);
			
			rs=pr.executeQuery();
			if(rs.next()) {
				y=rs.getString("price");
			}
		
		}catch(Exception e) {
			e.printStackTrace();
		}
	return y;
	}
	
	public static void addcust(String car_id,String fname,String lname,String gend,String price,String drent,String dreturn) {
		String query="INSERT INTO customer (car_id, firstname, lastname, gender,  total, date_rent, date_return) values (?,?,?,?,?,?,?) ;";
				
		con=Database.connectDb();
		try {
			
			pr=con.prepareStatement(query);
			pr.setString(1, car_id);
			pr.setString(2, fname);
			pr.setString(3, lname);
			pr.setString(4, gend);
			pr.setString(5, price);
			pr.setString(6, drent);
			pr.setString(7, dreturn);
			pr.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static ObservableList<Customerdata> getcust(){
		ObservableList<Customerdata> lcar=FXCollections.observableArrayList();
		String query="select * from customer;";
		 con = Database.connectDb();
		 try {
			 pr=con.prepareStatement(query);
			 rs=pr.executeQuery();
			 Customerdata cd;
			 while(rs.next()) {
				 cd = new Customerdata(rs.getString("firstname"),
						 rs.getString("lastname"),
                         rs.getString("gender"),
                         rs.getString("car_id"),
                         rs.getString("total"),
                         rs.getString("status"),
                         rs.getString("date_rent"),
                         rs.getString("date_return")
						 );

                lcar.add(cd);
			 }
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		 return lcar;
		 
	}
	public static void updatestatus(String carid) {
		String query="update car set status='--Not Available--' where car_id=?;";
		con=Database.connectDb();
		try {
			
			pr=con.prepareStatement(query);
			pr.setString(1,carid);
			pr.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void updatestatus1(String carid) {
		String query="update car set status='--Available--' where car_id=?;";
		con=Database.connectDb();
		try {
			
			pr=con.prepareStatement(query);
			pr.setString(1,carid);
			pr.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void updatestatus2(String carid) {
		String query="update customer set status='return' where car_id=?;";
		con=Database.connectDb();
		try {
			
			pr=con.prepareStatement(query);
			pr.setString(1,carid);
			pr.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
