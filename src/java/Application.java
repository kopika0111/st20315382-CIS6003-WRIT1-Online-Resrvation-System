
import com.restaurant.dao.UserDAO;
import com.restaurant.model.User;
import com.restaurant.observer.UserObserver;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hp
 */
public class Application {
    public static void main(String[] args) throws SQLException {
        UserDAO userDAO = new UserDAO();
        UserObserver userObserver = new UserObserver();

        // Register the observer
        userDAO.addObserver(userObserver);

        // Example user update
        User user = userDAO.getUserByUsername("exampleUser");
        if (user != null) {
            user.setEmail("newemail@example.com");
            try {
                userDAO.updateUser(user);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

