package org.labirynthShiftChat.dao;

import org.labirynthShiftChat.model.GameSession;
import org.labirynthShiftChat.model.Player;
import org.labirynthShiftChat.model.Tile;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.labirynthShiftChat.config.HibernateUtil;

public class GameSessionDAO {

    public void save(GameSession gameSession) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // ✅ Salviamo la posizione del giocatore nella sessione
            Tile currentTile = gameSession.getCurrentTile();
            gameSession.setCurrentTile(currentTile);

            session.persist(gameSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Errore durante il salvataggio della sessione di gioco", e);
        }
    }

    public GameSession findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            GameSession gameSession = session.get(GameSession.class, id);
            if (gameSession != null) {
                // ✅ Ripristiniamo il giocatore nella sua posizione corrente
                gameSession.setPlayer(new Player(gameSession.getCurrentTile().getX(), gameSession.getCurrentTile().getY()));
            }
            return gameSession;
        }
    }

    public void update(GameSession gameSession) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // ✅ Aggiorniamo la posizione del giocatore nella sessione
            Tile currentTile = gameSession.getCurrentTile();
            gameSession.setCurrentTile(currentTile);

            session.merge(gameSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Errore durante l'aggiornamento della sessione di gioco", e);
        }
    }
}
