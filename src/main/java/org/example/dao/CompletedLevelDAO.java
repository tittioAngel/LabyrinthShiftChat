package org.example.dao;

import org.example.config.HibernateUtil;
import org.example.model.CompletedLevel;
import org.example.model.Profile;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CompletedLevelDAO {

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

    public CompletedLevel findByLevelAndProfile(Long levelID, Long profileID) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM CompletedLevel " +
                            "WHERE level.id = :levelID " +
                            "AND profile.id = :profileID ", CompletedLevel.class)
                    .setParameter("levelID", levelID)
                    .setParameter("profileID", profileID)
                    .uniqueResult();
        }
    }

    public void update(CompletedLevel completedLevel) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.merge(completedLevel);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Errore durante l'aggiornamento", e);
        }
    }
}
