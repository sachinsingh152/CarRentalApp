package application.service;

import org.mindrot.jbcrypt.BCrypt;
import application.dao.AdminDAO;

public class AuthService {
    private AdminDAO adminDAO = new AdminDAO();
    
    // Store authenticated user session state
    public static String currentUsername = null;

    public boolean login(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }
        
        String hash = adminDAO.getPasswordHash(username);
        if (hash != null) {
            try {
                if (BCrypt.checkpw(password, hash)) {
                    currentUsername = username;
                    return true;
                }
            } catch (Exception e) {
                System.err.println("Error verifying BCrypt hash. (Make sure DB passwords are encrypted).");
            }
        }
        return false;
    }
    
    public void logout() {
        currentUsername = null;
    }
}
