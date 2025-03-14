package org.labirynthShiftChat.dao;

import lombok.NoArgsConstructor;
import org.labirynthShiftChat.config.HibernateUtil;
import org.labirynthShiftChat.model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

@NoArgsConstructor
public class ProfileDAO {

    public Profile findByUsernameAndPassword(final String username, final String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Profile " +
                            "WHERE username = :username " +
                            "AND password = :password", Profile.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .uniqueResult();
        }
    }

    public boolean save(Profile profile) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Profile existingProfile = session.createQuery("FROM Profile WHERE username = :username", Profile.class)
                    .setParameter("username", profile.getUsername())
                    .uniqueResult();

            if (existingProfile != null) {
                System.out.println("⚠️ Un profilo con username '" + profile.getUsername() + "' esiste già!");
                return false;
            }

            session.persist(profile);
            transaction.commit();
            System.out.println("🎮 Un profilo con username '" + profile.getUsername() + "' creato con successo!");
            return true;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Errore durante il salvataggio del nuovo profilo", e);
        }
    }

    public List<CompletedLevel> findCompletedLevelsByProfile(final Profile profile) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM CompletedLevel " +
                            "WHERE profile.id = :profileId ", CompletedLevel.class)
                    .setParameter("profileId", profile.getId())
                    .getResultList();
        }
    }

    public static Profile findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Profile.class, id);
        }
    }

    public Profile findByUsername(final String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT p FROM Profile p LEFT JOIN FETCH p.completedLevels WHERE p.username = :username", Profile.class)
                    .setParameter("username", username)
                    .uniqueResult();
        }
    }

    public void update(Profile profile) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();


            session.merge(profile);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Errore durante l'aggiornamento della sessione di gioco", e);
        }
    }
}
