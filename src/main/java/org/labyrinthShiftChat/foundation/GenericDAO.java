package org.labyrinthShiftChat.foundation;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.labyrinthShiftChat.foundation.config.HibernateUtil;

import java.io.Serializable;

public abstract class GenericDAO<T> {

    private final Class<T> clazz;

    protected GenericDAO(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void save(T entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Errore durante il salvataggio", e);
        }
    }

    public void update(T entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Errore durante l'aggiornamento", e);
        }
    }

    public T findById(Serializable id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(clazz, id);
        }
    }

    public void delete(T entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Errore durante l'eliminazione", e);
        }
    }
}
