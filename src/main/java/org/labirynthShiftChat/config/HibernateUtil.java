package org.labirynthShiftChat.config;

import org.labirynthShiftChat.model.*;
import org.labirynthShiftChat.model.entities.Adversity;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Player.class)
                    .addAnnotatedClass(Profile.class)
                    .addAnnotatedClass(Level.class)
                    .addAnnotatedClass(Maze.class)
                    .addAnnotatedClass(Tile.class)
                    .addAnnotatedClass(Adversity.class)
                    .addAnnotatedClass(GameSession.class)
                    .addAnnotatedClass(CompletedLevel.class)
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
