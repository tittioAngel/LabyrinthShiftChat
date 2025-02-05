package org.example.dao;

import org.example.model.tiles.Corridor;
import org.hibernate.Session;
import org.example.config.HibernateUtil;

import java.util.List;

public class CorridorDAO {

    public void save(Corridor corridor) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(corridor);
            session.getTransaction().commit();
        }
    }

    public List<Corridor> findAllCorridorsByMaze(Long mazeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Corridor WHERE maze.id = :mazeId", Corridor.class)
                    .setParameter("mazeId", mazeId)
                    .list();
        }
    }
}
