package application;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database  implements DatabaseInt
{
	public static Connection connectDb() 
	{
        try
        {
        	
        	Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, root, password); 
            return con;
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        return null;
    }
}