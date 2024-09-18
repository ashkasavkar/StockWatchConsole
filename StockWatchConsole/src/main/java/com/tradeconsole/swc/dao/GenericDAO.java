package com.tradeconsole.swc.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.tradeconsole.swc.util.HibernateUtil;

import java.io.Serializable;
import java.util.List;

public abstract class GenericDAO<T, ID extends Serializable> {

    private Class<T> persistentClass;

    public GenericDAO(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    // Method to get the current Hibernate session
    protected Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

    // Method to save an entity to the database
    public void save(T entity) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    // Method to update an existing entity in the database
    public void update(T entity) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            session.update(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    // Method to delete an entity from the database
    public void delete(T entity) {
        Session session = getSession();
        Transaction tx = session.beginTransaction();
        try {
            session.delete(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    // Method to find an entity by its ID
    public T findById(ID id) {
        Session session = getSession();
        T entity = null;
        try {
            entity = session.get(persistentClass, id);
        } finally {
            session.close();
        }
        return entity;
    }

    // Method to find all entities of the type
    public List<T> findAll() {
        Session session = getSession();
        List<T> list = null;
        try {
            Query<T> query = session.createQuery("FROM " + persistentClass.getSimpleName(), persistentClass);
            list = query.list();
        } finally {
            session.close();
        }
        return list;
    }
}