package org.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.model.tiles.Corridor;
import org.example.model.Tile;
import org.hibernate.Session;
import org.example.config.HibernateUtil;

import java.util.List;

public class TileDAO {

    public void save(Tile tile) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(tile);
            session.getTransaction().commit();
        }
    }

    public Tile findTileByPosition(int x, int y, Long mazeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Tile WHERE x = :x AND y = :y AND maze.id = :mazeId", Tile.class)
                    .setParameter("x", x)
                    .setParameter("y", y)
                    .setParameter("mazeId", mazeId)
                    .uniqueResult();
        }
    }

    public List<Tile> findAllTilesByMaze(Long mazeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Tile WHERE maze.id = :mazeId", Tile.class)
                    .setParameter("mazeId", mazeId)
                    .list();
        }
    }

    public Corridor findStartingTile(Long mazeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Corridor WHERE maze.id = :mazeId", Corridor.class)
                    .setParameter("mazeId", mazeId)
                    .setMaxResults(1)
                    .uniqueResult();
        }
    }

    public void merge(Tile tile) {
        EntityManager entityManager = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(tile); // 🔥 Usa `merge()` anziché `persist()`
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }
}
