package org.example.service;

import org.example.model.Player;
import org.example.dao.PlayerDAO;

import java.util.List;

public class PlayerService {
    private final PlayerDAO playerDAO = new PlayerDAO();

    public void createPlayer(String username) {
        Player player = new Player(username);
        playerDAO.save(player);
    }

    public Player getPlayerById(Long id) {
        return playerDAO.findById(id);
    }

    public Player getPlayerByName(String username) {
        return playerDAO.findByUsername(username);
    }

    public List<Player> getAllPlayers() {
        return playerDAO.findAll();
    }
}
