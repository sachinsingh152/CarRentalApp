package application.config;

import java.sql.Connection;
import java.sql.DriverManager;
import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseConfig {
    
    private static Dotenv dotenv;

    static {
        try {
            dotenv = Dotenv.configure().ignoreIfMissing().load();
        } catch (Exception e) {
            System.err.println("Notice: Could not load .env file. Falling back to system environment variables.");
        }
    }

    public static Connection connectDb() {
        try {
            String url = dotenv != null ? dotenv.get("DB_URL") : System.getenv("DB_URL");
            String username = dotenv != null ? dotenv.get("DB_USERNAME") : System.getenv("DB_USERNAME");
            String password = dotenv != null ? dotenv.get("DB_PASSWORD") : System.getenv("DB_PASSWORD");

            if (url == null || url.isEmpty()) {
                url = "jdbc:mysql://localhost/rentcar";
            }
            if (username == null || username.isEmpty()) {
                username = "root";
            }
            if (password == null) {
                password = "";
            }
            
            System.out.println("DEBUG DatabaseConfig: URL=" + url + ", USER=" + username + ", PASS_LENGTH=" + password.length());
            System.out.println("DEBUG dotenv loaded: " + (dotenv != null));
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password);
            return con;
        } catch(Exception e) {
            System.err.println("Database connection failed:");
            e.printStackTrace();
        }
        return null;
    }
}