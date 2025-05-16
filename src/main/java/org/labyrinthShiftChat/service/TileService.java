package org.labyrinthShiftChat.service;

import org.labyrinthShiftChat.foundation.MazeComponentDAO;
import org.labyrinthShiftChat.foundation.GameSessionDAO;
import org.labyrinthShiftChat.foundation.TileDAO;
import org.labyrinthShiftChat.model.Maze;
import org.labyrinthShiftChat.model.Player;
import org.labyrinthShiftChat.model.Tile;
import org.labyrinthShiftChat.model.tiles.MazeComponent;
import org.labyrinthShiftChat.model.tiles.common.Corridor;
import org.labyrinthShiftChat.singleton.GameSessionManager;

import java.util.ArrayList;
import java.util.List;

public class TileService {

    private final GameSessionManager gameSessionManager = GameSessionManager.getInstance();

    private final MazeComponentDAO mazeComponentDAO = new MazeComponentDAO();
    private final TileDAO tileDAO = new TileDAO();
    private final GameSessionDAO gameSessionDAO = new GameSessionDAO();

    public void checkTileEffects() {

        Maze maze = gameSessionManager.getGameSession().getMaze();
        Player player = gameSessionManager.getGameSession().getPlayer();

        MazeComponent mazeComponent = mazeComponentDAO.checkIfTileIsActiveByMaze(maze.getId(), player);

        if (mazeComponent != null) {
            mazeComponent.applyEffect(player);
            mazeComponentDAO.update(mazeComponent);

            gameSessionManager.getGameSession().setCurrentTile(tileDAO.findTileByPosition(player.getX(), player.getY(), maze.getId()));
            gameSessionDAO.update(gameSessionManager.getGameSession());

        }

    }

    public List<Tile> findAllAvailableTiles(List<Tile> allTiles) {
        List<Tile> availableTiles = new ArrayList<>();

        for (Tile tile : allTiles) {
            MazeComponent mazeComponent = mazeComponentDAO.findByTile(tile);
            if (mazeComponent != null) {
                if (mazeComponent instanceof Corridor) {
                    availableTiles.add(tile);
                }
            }
        }

        return availableTiles;
    }

}
