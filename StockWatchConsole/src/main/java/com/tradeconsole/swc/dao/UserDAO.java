package com.tradeconsole.swc.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.tradeconsole.swc.entity.User;

public class UserDAO extends GenericDAO<User, Long> {

    public UserDAO() {
        super(User.class);
    }

    // Custom query method to find a user by username
    public User findByUsername(String username) {
        Session session = getSession();
        try {
            Query<User> query = session.createQuery("FROM User WHERE username = :username", User.class);
            query.setParameter("username", username);
            return query.uniqueResult();
        } finally {
            session.close();
        }
    }
 // Find a user by email
    public User findByEmail(String email) {
        Session session = getSession();
        try {
            Query<User> query = session.createQuery("FROM User WHERE email = :email", User.class);
            query.setParameter("email", email);
            return query.uniqueResult();
        } finally {
            session.close();
        }
    }

    
}