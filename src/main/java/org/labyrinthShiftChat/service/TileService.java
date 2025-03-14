package org.labyrinthShiftChat.service;

import org.labyrinthShiftChat.dao.AdversityDAO;
import org.labyrinthShiftChat.dao.GameSessionDAO;
import org.labyrinthShiftChat.dao.TileDAO;
import org.labyrinthShiftChat.model.GameSession;
import org.labyrinthShiftChat.model.Maze;
import org.labyrinthShiftChat.model.Player;
import org.labyrinthShiftChat.model.entities.Adversity;

public class TileService {

    private final AdversityDAO adversityDAO = new AdversityDAO();
    private final TileDAO tileDAO = new TileDAO();
    private final GameSessionDAO gameSessionDAO = new GameSessionDAO();

    public void checkTileEffects(GameSession gameSession, Player player) {
        Maze maze = gameSession.getMaze();

        Adversity adversity = adversityDAO.findAllActiveByMaze(maze.getId())
                .stream()
                .filter(a -> a.getX() == player.getX() && a.getY() == player.getY())
                .findFirst()
                .orElse(null);

        if (adversity != null) {
            adversity.applyEffect(player);
            adversityDAO.update(adversity);

            gameSession.setCurrentTile(
                    tileDAO.findTileByPosition(player.getX(), player.getY(), maze.getId()));
            gameSessionDAO.update(gameSession);

            System.out.println("🎭 Effetto applicato: " + adversity.getAdversityType());
        }

    }
}
