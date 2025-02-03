//package org.example.controller;
//
//import org.example.model.Player;
//import org.example.service.PlayerService;
//import org.example.singleton.ServiceFactory;
//
//import java.util.List;
//
//public class PlayerController {
//
//    private final PlayerService playerService;
//
//    public PlayerController() {
//        this.playerService = ServiceFactory.getInstance().getPlayerService();
//    }
//
//    public void registerPlayer(String username) {
//        playerService.registerPlayer(username);
//        System.out.println("Player registered: " + username);
//    }
//
//    public void displayPlayer(Long id) {
//        Player player = playerService.getPlayerById(id);
//        if (player != null) {
//            System.out.println("Player: " + player.getUsername() + ", Score: " + player.getHighestScore());
//        } else {
//            System.out.println("Player not found.");
//        }
//    }
//
//    public void displayAllPlayers() {
//        List<Player> players = playerService.getAllPlayers();
//        players.forEach(p -> System.out.println(p.getUsername() + " - Score: " + p.getHighestScore()));
//    }
//
//    public void updatePlayerScore(Long id, int newScore) {
//        Player player = playerService.getPlayerById(id);
//        if (player != null) {
//            player.setHighestScore(newScore);
//            playerService.updatePlayer(player);
//            System.out.println("Updated score for " + player.getUsername() + " to " + newScore);
//        } else {
//            System.out.println("Player not found.");
//        }
//    }
//}
