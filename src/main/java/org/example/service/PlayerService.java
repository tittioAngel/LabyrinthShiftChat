package org.example.service;

import org.example.model.Player;
import org.example.dao.PlayerDAO;
import org.example.util.MenuUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class PlayerService {

    private final PlayerDAO playerDAO = new PlayerDAO();

    private final MenuUtil menuUtil = new MenuUtil();

    public Player getPlayerById(Long id) {
        return playerDAO.findById(id);
    }

    public Player getPlayerByName(String username) {
        return playerDAO.findByUsername(username);
    }

    public List<Player> getAllPlayers() {
        return playerDAO.findAll();
    }

    public Player playerLogin(final String username, final String password) {
        final Player player = getPlayerByUsernameAndPassword(username, password);
        if (player == null) {
            int scelta = menuUtil.showStartGameMenu(false);

            switch (scelta) {
                case 1:
                    final HashMap<String, String> credentialsLogin = menuUtil.showCredentialsMenu();
                    playerLogin(credentialsLogin.get("username"), credentialsLogin.get("password"));
                    break;
                case 2:
                    final HashMap<String, String> credentialsSignUp = menuUtil.showCredentialsMenu();
                    createPlayer(credentialsSignUp.get("username"), credentialsSignUp.get("password"));
                    break;
                case 3:
                    System.out.println("\n👋 Grazie per aver giocato! Alla prossima! 🎮");
                    return null;
                default:
                    System.out.println("\n⚠️ Scelta non valida. Riprova.");
            }
        }
        return player;
    }

    public Player getPlayerByUsernameAndPassword(final String username, final String password) {
        return playerDAO.findByUsernameAndPassword(username, password);
    }

    public void createPlayer(final String username,final String password) {
        Player player = new Player(username, password);
        playerDAO.save(player);
    }

}
