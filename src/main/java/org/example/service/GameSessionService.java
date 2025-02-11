package org.example.service;

import org.example.dao.*;
import org.example.model.*;
import org.example.model.entities.Adversity;
import org.example.model.tiles.Corridor;
import org.example.model.tiles.Wall;

import java.util.List;
import java.util.stream.Collectors;

public class GameSessionService {
    private final GameSessionDAO gameSessionDAO = new GameSessionDAO();
     private final Player player=new Player();
    private final MazeDAO mazeDAO = new MazeDAO();
    private final TileDAO tileDAO = new TileDAO();

    public GameSession startGame(Long profileId, Long mazeId) {

        //questa parte potrebbe essere inutile almeno per il profilo
        Profile profile = ProfileDAO.findById(profileId);
        Maze maze = mazeDAO.findById(mazeId);
        if (profile == null || maze == null) {
            throw new RuntimeException("❌ Giocatore o labirinto non trovati.");
        }
        // Trova la posizione di partenza (es. primo corridoio disponibile)
        Tile startTile = tileDAO.findStartingTile(mazeId);
        if (startTile == null) {
            throw new RuntimeException("❌ Nessuna tile di partenza trovata nel labirinto!");
        }

        // Crea una nuova sessione di gioco
        GameSession gameSession = new GameSession(profile, maze,startTile, 60);
        gameSessionDAO.save(gameSession);

        System.out.println("✅ Partita avviata per " + profile.getUsername() + " nel labirinto di livello " + maze.getLevel());
        System.out.println("📍 Posizione iniziale: (" + startTile.getX() + ", " + startTile.getY() + ")");
        System.out.println("⏳ Tempo rimanente: 60 secondi.");

        return gameSession;
    }


    // verificare se il giocatore incontra un ostacolo o un nemico.
    public void checkTileEffects(GameSession gameSession, Player player) {
        Maze maze = gameSession.getMaze();
        int playerX = player.getX();
        int playerY = player.getY();

        AdversityDAO adversityDAO = new AdversityDAO();
        List<Adversity> adversities = adversityDAO.findAllByMaze(maze.getId())
                .stream()
                .filter(a -> a.getX() == playerX && a.getY() == playerY)
                .collect(Collectors.toList());

        for (Adversity adversity : adversities) {
            if (!adversity.isActivated()) {
                adversity.triggerEffect(player);
                adversity.setActivated(true);
            }
        }
    }

    public void movePlayer(Long sessionId, String direction) {
        GameSession gameSession = gameSessionDAO.findById(sessionId);
        if (gameSession == null) {
            System.out.println("❌ Sessione di gioco non trovata.");
            return;
        }

        Tile currentTile = gameSession.getCurrentTile();
        Tile newTile = null;

        switch (direction.toUpperCase()) {
            case "W": // Su
                newTile = tileDAO.findTileByPosition(currentTile.getX(), currentTile.getY() - 1, currentTile.getMaze().getId());
                break;
            case "S": // Giù
                newTile = tileDAO.findTileByPosition(currentTile.getX(), currentTile.getY() + 1, currentTile.getMaze().getId());
                break;
            case "A": // Sinistra
                newTile = tileDAO.findTileByPosition(currentTile.getX() - 1, currentTile.getY(), currentTile.getMaze().getId());
                break;
            case "D": // Destra
                newTile = tileDAO.findTileByPosition(currentTile.getX() + 1, currentTile.getY(), currentTile.getMaze().getId());
                break;
            default:
                System.out.println("❌ Direzione non valida. Usa: W, A, S, D.");
                return;
        }

        if (newTile == null) {
            System.out.println("❌ Movimento non consentito! Sei fuori dai confini del labirinto.");
            return;
        }

        if (newTile instanceof Wall) {
            System.out.println("🚧 Hai colpito un muro! Non puoi passare.");
            return;
        }

        if (newTile instanceof Corridor) {
            gameSession.setCurrentTile(newTile);
            gameSessionDAO.update(gameSession);
            System.out.println("✅ Ti sei mosso in posizione: (" + newTile.getX() + ", " + newTile.getY() + ")");
        } else {
            System.out.println("❌ Movimento non valido.");
        }

        // 🔥 Verifica se il giocatore incontra ostacoli o nemici
        checkTileEffects(gameSession, new Player(newTile.getX(), newTile.getY()));
    }
}
