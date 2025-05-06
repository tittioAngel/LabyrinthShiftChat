package org.labyrinthShiftChat.dao;

import org.hibernate.Session;
import org.labyrinthShiftChat.config.HibernateUtil;
import org.hibernate.Transaction;
import org.labyrinthShiftChat.model.Player;
import org.labyrinthShiftChat.model.Tile;
import org.labyrinthShiftChat.model.tiles.MazeComponent;

import java.util.List;

public class MazeComponentDAO {

    public MazeComponent findByTile(Tile tile) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM MazeComponent WHERE tile.id = :tileId", MazeComponent.class)
                    .setParameter("tileId", tile.getId()).stream().findFirst().orElse(null);
        }
    }

    public void save(MazeComponent mazeComponent) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(mazeComponent);
            session.getTransaction().commit();
        }
    }

    public void merge(MazeComponent mazeComponent) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            MazeComponent existing = session
                    .createQuery("FROM MazeComponent WHERE tile.id = :tileId", MazeComponent.class)
                    .setParameter("tileId", mazeComponent.getTile().getId())
                    .uniqueResult();

            if (existing != null) {
                session.remove(existing);
                session.flush();
            }

            session.persist(mazeComponent);

            session.getTransaction().commit();
        }
    }

    public MazeComponent checkIfTileIsActiveByMaze(Long mazeId, Player player) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "FROM MazeComponent " +
                            "WHERE tile.maze.id = :mazeId " +
                            "AND tile.x = :playerPos_x " +
                            "AND tile.y = :playerPos_y " +
                            "AND activated = false", MazeComponent.class)
                    .setParameter("mazeId", mazeId)
                    .setParameter("playerPos_x", player.getX())
                    .setParameter("playerPos_y", player.getY())
                    .stream().findFirst().orElse(null);
        }
    }

    public void update(MazeComponent mazeComponent) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(mazeComponent);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
