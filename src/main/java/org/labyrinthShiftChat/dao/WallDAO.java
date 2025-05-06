package org.labyrinthShiftChat.dao;

import org.labyrinthShiftChat.model.tiles.common.Wall;
import org.hibernate.Session;
import org.labyrinthShiftChat.config.HibernateUtil;

import java.util.List;

public class WallDAO {

    public void save(Wall wall) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(wall);
            session.getTransaction().commit();
        }
    }

    public List<Wall> findAllWallsByMaze(Long mazeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Wall WHERE maze.id = :mazeId", Wall.class)
                    .setParameter("mazeId", mazeId)
                    .list();
        }
    }
}
