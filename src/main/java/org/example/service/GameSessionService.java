package org.example.service;

import org.example.dao.GameSessionDAO;
import org.example.dao.PlayerDAO;
import org.example.dao.MazeDAO;
import org.example.dao.TileDAO;
import org.example.model.*;

public class GameSessionService {
    private final GameSessionDAO gameSessionDAO = new GameSessionDAO();
    private final PlayerDAO playerDAO = new PlayerDAO();
    private final MazeDAO mazeDAO = new MazeDAO();
    private final TileDAO tileDAO = new TileDAO();

    public GameSession startGame(Long playerId, Long mazeId) {
        Player player = playerDAO.findById(playerId);
        Maze maze = mazeDAO.findById(mazeId);
        if (player == null || maze == null) {
            throw new RuntimeException("❌ Giocatore o labirinto non trovati.");
        }
        // Trova la posizione di partenza (es. primo corridoio disponibile)
        Tile startTile = tileDAO.findStartingTile(mazeId);
        if (startTile == null) {
            throw new RuntimeException("❌ Nessuna tile di partenza trovata nel labirinto!");
        }

        // Crea una nuova sessione di gioco
        GameSession gameSession = new GameSession(player, maze,startTile, 60);
        gameSessionDAO.save(gameSession);

        System.out.println("✅ Partita avviata per " + player.getUsername() + " nel labirinto di livello " + maze.getLevel());
        System.out.println("📍 Posizione iniziale: (" + startTile.getX() + ", " + startTile.getY() + ")");
        System.out.println("⏳ Tempo rimanente: 60 secondi.");

        return gameSession;
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
    }
}
