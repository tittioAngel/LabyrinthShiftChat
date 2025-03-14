package org.labyrinthShiftChat.dao;

import org.labyrinthShiftChat.model.entities.Adversity;
import org.hibernate.Session;
import org.labyrinthShiftChat.config.HibernateUtil;
import org.hibernate.Transaction;

import java.util.List;

public class AdversityDAO {

    public void save(Adversity adversity) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(adversity);
            session.getTransaction().commit();
        }
    }

    public List<Adversity> findAllByMaze(Long mazeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Adversity WHERE maze.id = :mazeId", Adversity.class)
                    .setParameter("mazeId", mazeId)
                    .list();
        }
    }

    public List<Adversity> findAllActiveByMaze(Long mazeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Adversity WHERE maze.id = :mazeId AND activated = false", Adversity.class)
                    .setParameter("mazeId", mazeId)
                    .list();
        }
    }

    // Metodo per aggiornare lo stato di un'istanza di Adversity
    public void update(Adversity adversity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(adversity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
