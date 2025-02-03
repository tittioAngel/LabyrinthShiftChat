package org.example.dao;

import org.example.model.Player;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.example.config.HibernateUtil;

import java.util.List;

public class PlayerDAO {

    public void save(Player player) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // ✅ Controlla se il giocatore esiste già per evitare la violazione della chiave unica
            Player existingPlayer = session.createQuery("FROM Player WHERE username = :username", Player.class)
                    .setParameter("username", player.getUsername())
                    .uniqueResult();

            if (existingPlayer != null) {
                System.out.println("⚠️ Il giocatore con username '" + player.getUsername() + "' esiste già!");
                return;
            }

            session.persist(player);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Errore durante il salvataggio del giocatore", e);
        }
    }

    // ✅ Metodo findById aggiunto
    public Player findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Player.class, id);
        }
    }

    // ✅ Metodo findAll aggiunto
    public List<Player> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Player", Player.class).list();
        }

    }

    public Player findByUsername(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Player WHERE username = :username", Player.class)
                    .setParameter("username", username)
                    .uniqueResult();
        }
    }
}
