package org.labyrinthShiftChat.foundation;

import org.hibernate.Session;
import org.labyrinthShiftChat.foundation.config.HibernateUtil;
import org.labyrinthShiftChat.model.Tile;

import java.util.List;

public class TileDAO extends GenericDAO<Tile> {

    public TileDAO() {
        super(Tile.class);
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

}
