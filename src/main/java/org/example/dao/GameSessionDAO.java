package org.example.dao;

import org.example.model.GameSession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.example.config.HibernateUtil;

public class GameSessionDAO {

    public void save(GameSession gameSession) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(gameSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Errore durante il salvataggio della sessione di gioco", e);
        }
    }

    public GameSession findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(GameSession.class, id);
        }
    }

    public void update(GameSession gameSession) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(gameSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Errore durante l'aggiornamento della sessione di gioco", e);
        }
    }
}
