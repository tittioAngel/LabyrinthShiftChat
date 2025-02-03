package org.example.dao;

import org.example.model.Obstacle;
import org.hibernate.Session;
import org.example.config.HibernateUtil;

import java.util.List;

public class ObstacleDAO {

    public void save(Obstacle obstacle) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(obstacle);
            session.getTransaction().commit();
        }
    }

    public List<Obstacle> findAllObstaclesByMaze(Long mazeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Obstacle WHERE maze.id = :mazeId", Obstacle.class)
                    .setParameter("mazeId", mazeId)
                    .list();
        }
    }
}
