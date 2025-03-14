package org.labirynthShiftChat.dao;

import org.labirynthShiftChat.model.Maze;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.labirynthShiftChat.config.HibernateUtil;

import java.util.List;

public class MazeDAO {

    public void save(Maze maze) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(maze);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Maze findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Maze.class, id);
        }
    }

    public Maze findByLevel(int level) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Maze> results = session.createQuery("FROM Maze WHERE level = :level", Maze.class)
                    .setParameter("level", level)
                    .getResultList();  // ✅ Usa getResultList() per evitare l'errore

            if (results.isEmpty()) {
                return null;  // ✅ Nessun labirinto trovato
            }
            return results.get(0);  // ✅ Restituisce il primo labirinto trovato
        }
    }

    public List<Maze> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Maze", Maze.class).list();
        }
    }
}
