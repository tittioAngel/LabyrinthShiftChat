package org.example.dao;

import org.example.config.HibernateUtil;
import org.example.model.CompletedLevel;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CompletedLevelDao {

    public void save(CompletedLevel completedLevel) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.persist(completedLevel);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Errore durante il salvataggio della sessione di gioco", e);
        }
    }
}
