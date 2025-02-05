package org.example.dao;

import org.example.model.entities.Enemy;
import org.hibernate.Session;
import org.example.config.HibernateUtil;

import java.util.List;

public class EnemyDAO {

    public void save(Enemy enemy) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(enemy);
            session.getTransaction().commit();
        }
    }

    public List<Enemy> findAllEnemiesByMaze(Long mazeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Enemy WHERE maze.id = :mazeId", Enemy.class)
                    .setParameter("mazeId", mazeId)
                    .list();
        }
    }
}
