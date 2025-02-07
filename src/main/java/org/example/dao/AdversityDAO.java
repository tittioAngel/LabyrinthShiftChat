package org.example.dao;

import org.example.model.entities.Adversity;
import org.hibernate.Session;
import org.example.config.HibernateUtil;

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
}
