package org.labyrinthShiftChat.foundation;

import org.labyrinthShiftChat.foundation.config.HibernateUtil;
import org.labyrinthShiftChat.model.GameSession;
import org.labyrinthShiftChat.model.Tile;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class GameSessionDAO extends GenericDAO<GameSession> {

    public GameSessionDAO() {
        super(GameSession.class);
    }

    @Override
    public void save(GameSession gameSession) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Tile currentTile = gameSession.getCurrentTile();
            gameSession.setCurrentTile(currentTile);

            session.persist(gameSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Errore durante il salvataggio della sessione di gioco", e);
        }
    }

    @Override
    public void update(GameSession gameSession) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

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
