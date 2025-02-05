package org.example.config;

import org.example.model.*;
import org.example.model.entities.Enemy;
import org.example.model.entities.Obstacle;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration()
                    .configure("hibernate.cfg.xml") // Carica la configurazione di base
                    .addAnnotatedClass(Player.class)
                    .addAnnotatedClass(Maze.class)
                    .addAnnotatedClass(Tile.class)
                    .addAnnotatedClass(Obstacle.class)
                    .addAnnotatedClass(Enemy.class)
                    .addAnnotatedClass(GameSession.class)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError("Errore nella creazione della SessionFactory: " + ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}
