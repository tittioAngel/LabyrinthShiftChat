package org.labirynthShiftChat.service;


import org.labirynthShiftChat.dao.GameSessionDAO;
import org.labirynthShiftChat.dao.TileDAO;
import org.labirynthShiftChat.model.GameSession;
import org.labirynthShiftChat.model.Player;
import org.labirynthShiftChat.model.Tile;
import org.labirynthShiftChat.model.tiles.Wall;
import org.labirynthShiftChat.singleton.GameSessionManager;

public class PlayerService {

    private final GameSessionManager gameSessionManager = GameSessionManager.getInstance();
    private final TileService tileService = new TileService();
    private final TileDAO tileDAO = new TileDAO();
    private final GameSessionDAO gameSessionDAO = new GameSessionDAO();

    public Tile movePlayerOnNewTile(String direction) {
        GameSession gameSession = gameSessionManager.getGameSession();
        Player player = gameSession.getPlayer();

        Tile newTile;
        switch (direction) {
            case "W": // SU
                newTile = tileDAO.findTileByPosition(player.getX(), player.getY() - 1, gameSession.getMaze().getId());
                break;
            case "S": // GIÙ
                newTile = tileDAO.findTileByPosition(player.getX(), player.getY() + 1, gameSession.getMaze().getId());
                break;
            case "A": // SINISTRA
                newTile = tileDAO.findTileByPosition(player.getX() - 1, player.getY(), gameSession.getMaze().getId());
                break;
            case "D": // DESTRA
                newTile = tileDAO.findTileByPosition(player.getX() + 1, player.getY(), gameSession.getMaze().getId());
                break;
            default:
                System.out.println("[DEBUG] Comando non valido: " + direction);
                System.out.println("❌ Direzione non valida. Usa: W, A, S, D.");
                return null;
        }

        if (newTile == null) {
            System.out.println("[DEBUG] ❌ Movimento non consentito! Sei fuori dai confini del labirinto.");
            return null;
        }

        if (newTile instanceof Wall) {
            System.out.println("[DEBUG] 🚧 Hai colpito un muro! Non puoi passare.");
            return null;
        }

        player.setPosition(newTile.getX(), newTile.getY());
        gameSession.setCurrentTile(newTile);
        gameSessionDAO.update(gameSession);

        return newTile;
    }

}
