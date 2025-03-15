package org.labyrinthShiftChat.controller;

import org.labyrinthShiftChat.model.*;
import org.labyrinthShiftChat.model.tiles.ExitTile;
import org.labyrinthShiftChat.service.*;

import org.labyrinthShiftChat.singleton.GameSessionManager;

public class GameSessionController {

    private final GameSessionManager gameSessionManager = GameSessionManager.getInstance();

    private final StoryModeService gameSessionService = new StoryModeService();
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
        mazeService.createPreviewMiniMaze(gameSessionManager.getGameSession().getMaze());
    }

    public void regenerateMiniMaze() {
        gameSessionService.createOrRegenerateMazeInGameSession(true);
    }

    public void showLimitedMiniMazeView() {
        mazeService.createLimitedView(gameSessionManager.getGameSession().getMaze(),
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
