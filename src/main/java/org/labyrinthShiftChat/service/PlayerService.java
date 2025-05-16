package org.labyrinthShiftChat.service;


import org.labyrinthShiftChat.foundation.GameSessionDAO;
import org.labyrinthShiftChat.foundation.MazeComponentDAO;
import org.labyrinthShiftChat.foundation.TileDAO;
import org.labyrinthShiftChat.model.GameSession;
import org.labyrinthShiftChat.model.Player;
import org.labyrinthShiftChat.model.Tile;
import org.labyrinthShiftChat.model.tiles.MazeComponent;
import org.labyrinthShiftChat.model.tiles.common.Wall;
import org.labyrinthShiftChat.singleton.GameSessionManager;
import org.labyrinthShiftChat.util.controls.RotatingControls;

public class PlayerService {

    private final GameSessionManager gameSessionManager = GameSessionManager.getInstance();

    private final TileDAO tileDAO = new TileDAO();
    private final MazeComponentDAO mazeComponentDAO = new MazeComponentDAO();
    private final GameSessionDAO gameSessionDAO = new GameSessionDAO();

    public Tile movePlayerOnNewTile(RotatingControls.Direction direction) {
        GameSession gameSession = gameSessionManager.getGameSession();
        Player player = gameSession.getPlayer();

        Tile newTile;

        switch (direction) {
            case UP:
                newTile = tileDAO.findTileByPosition(player.getX(), player.getY() - 1, gameSession.getMaze().getId());
                break;
            case DOWN:
                newTile = tileDAO.findTileByPosition(player.getX(), player.getY() + 1, gameSession.getMaze().getId());
                break;
            case LEFT:
                newTile = tileDAO.findTileByPosition(player.getX() - 1, player.getY(), gameSession.getMaze().getId());
                break;
            case RIGHT:
                newTile = tileDAO.findTileByPosition(player.getX() + 1, player.getY(), gameSession.getMaze().getId());
                break;
            default:
                System.out.println("❌ Direzione non valida.");
                return null;
        }

        if (newTile == null) {
            System.out.println("❌ Movimento non consentito! Sei fuori dai confini del labirinto.");
            return null;
        }

        MazeComponent mazeComponent = mazeComponentDAO.findByTile(newTile);
        if (mazeComponent instanceof Wall) {
            System.out.println("🚧 Hai colpito un muro! Non puoi passare.");
            return null;
        }

        player.setPosition(newTile.getX(), newTile.getY());
        gameSession.setCurrentTile(newTile);
        gameSessionDAO.update(gameSession);

        return newTile;
    }

}
