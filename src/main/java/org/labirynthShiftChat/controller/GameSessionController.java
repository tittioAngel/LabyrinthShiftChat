package org.labirynthShiftChat.controller;

import org.labirynthShiftChat.model.*;
import org.labirynthShiftChat.model.tiles.ExitTile;
import org.labirynthShiftChat.service.*;

import org.labirynthShiftChat.singleton.GameSessionManager;

public class GameSessionController {

    private final GameSessionManager gameSessionManager = GameSessionManager.getInstance();

    private final GameSessionService gameSessionService = new GameSessionService();
    private final GameService gameService = new GameService();
    private final MazeService mazeService = new MazeService();
    private final PlayerService playerService = new PlayerService();
    private final ScoringService scoringService = new ScoringService();
    private final TileService tileService = new TileService();
    private final CompletedLevelService completedLevelService = new CompletedLevelService();
    private final ProfileService profileService = new ProfileService();

    public void createGameSession() {
        gameSessionService.createOrRegenerateMazeInGameSession(false);
    }

    public void showMiniMazePreview() {
        mazeService.previewMiniMaze(gameSessionManager.getGameSession().getMaze());
    }

    public void regenerateMiniMaze() {
        gameSessionService.createOrRegenerateMazeInGameSession(true);
    }

    public void showLimitedMiniMazeView() {
        mazeService.displayLimitedView(gameSessionManager.getGameSession().getMaze(),
                gameSessionManager.getGameSession().getCurrentTile().getX(),
                gameSessionManager.getGameSession().getCurrentTile().getY());
    }

    public int manageDirectionSelected(String direction, long startTime) {
        if (direction.equals("Q")) {
            System.out.println("❌ Hai abbandonato la partita.");
            gameService.stopGame();
        }

        Tile newTile = playerService.movePlayerOnNewTile(direction);

        if (newTile != null) {
            if (newTile instanceof ExitTile) {
                long totalTimeSeconds = (System.currentTimeMillis() - startTime) / 1000;
                return scoringService.computeStars(totalTimeSeconds);
            } else {
                tileService.checkTileEffects(gameSessionManager.getGameSession(), gameSessionManager.getGameSession().getPlayer());
            }
        }

        return 0;
    }

    public void saveCompletedLevel(int averageStars) {
        gameSessionService.manageSaveCompletedLevel(averageStars);
    }


}
