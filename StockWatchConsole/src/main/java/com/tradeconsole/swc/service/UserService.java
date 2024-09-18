package com.tradeconsole.swc.service;


import com.tradeconsole.swc.dao.UserDAO;
import com.tradeconsole.swc.entity.Nominee;
import com.tradeconsole.swc.entity.User;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    // Sign up a new user
    public void signUp(User user) {
        userDAO.save(user);
    }

    // Authenticate a user
    public User login(String username, String password) {
        User user = userDAO.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null; // Invalid credentials
    }

    // Add nominee details
    public void addNominee(Long userId, Nominee nominee) {
        User user = userDAO.findById(userId);
        if (user != null) {
            user.setNominee(nominee);
            userDAO.update(user);
        }
    }

    // Find a user by username
    public User findUserByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    // Find a user by email
    public User findUserByEmail(String email) {
        return userDAO.findByEmail(email);
    }
}