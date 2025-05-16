package org.labyrinthShiftChat.foundation;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.labyrinthShiftChat.foundation.config.HibernateUtil;
import org.labyrinthShiftChat.model.Player;
import org.labyrinthShiftChat.model.Tile;
import org.labyrinthShiftChat.model.tiles.MazeComponent;

public class MazeComponentDAO extends GenericDAO<MazeComponent> {

    public MazeComponentDAO() {
        super(MazeComponent.class);
    }

    public MazeComponent findByTile(Tile tile) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM MazeComponent WHERE tile.id = :tileId", MazeComponent.class)
                    .setParameter("tileId", tile.getId())
                    .uniqueResult();
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
                    .uniqueResult();
        }
    }

    public void mergeReplacingExisting(MazeComponent mazeComponent) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            MazeComponent existing = session
                    .createQuery("FROM MazeComponent WHERE tile.id = :tileId", MazeComponent.class)
                    .setParameter("tileId", mazeComponent.getTile().getId())
                    .uniqueResult();

            if (existing != null) {
                session.remove(existing);
                session.flush();
            }

            session.persist(mazeComponent);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Errore durante il merge del MazeComponent", e);
        }
    }
}
