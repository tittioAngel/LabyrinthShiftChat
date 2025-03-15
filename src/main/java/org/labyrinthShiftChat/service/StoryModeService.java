package org.labyrinthShiftChat.service;

import org.labyrinthShiftChat.dao.*;
import org.labyrinthShiftChat.model.*;
import org.labyrinthShiftChat.singleton.GameSessionManager;


public class StoryModeService {
    private final GameSessionManager gameSessionManager = GameSessionManager.getInstance();
    private final GameSessionDAO gameSessionDAO = new GameSessionDAO();
    private final MazeService mazeService = new MazeService();
    private final CompletedLevelService completedLevelService = new CompletedLevelService();
    private final ProfileService profileService = new ProfileService();

    public void createOrRegenerateMazeInGameSession(boolean isRegeneration) {
        System.out.println(isRegeneration ? "🔄 Rigenerazione del minimaze in corso..." : "");

        Maze maze = mazeService.generateRandomMaze(gameSessionManager.getLevelSelected().getDifficultyLevel());
        Tile startTile = mazeService.getStartTile(maze);

        GameSession gameSession;
        if (isRegeneration) {
            gameSession = gameSessionManager.getGameSession();
            gameSession.getPlayer().setPosition(startTile.getX(), startTile.getY());
            gameSession.setMaze(maze);
            gameSession.setCurrentTile(startTile);
            gameSession.setTimeRemaining(60);
            gameSessionDAO.update(gameSession);

            System.out.println("✅ Nuovo minimaze pronto! Il gioco riprende con la visione limitata.");

        } else {
            gameSession = new GameSession(maze, startTile, 60);
            saveGameSession(gameSession);
            gameSessionManager.setGameSession(gameSession);
        }
    }

    public void saveGameSession(GameSession gameSession) {
        gameSessionDAO.save(gameSession);
    }

    public void manageSaveCompletedLevel(int averageStars) {
        CompletedLevel completedLevelRetried = completedLevelService.getLevelRetried(gameSessionManager.getLevelSelected().getId(), gameSessionManager.getProfile().getId());

        if (completedLevelRetried != null) {
            if (completedLevelRetried.getScore() < averageStars) {
                completedLevelRetried.setScore(averageStars);
                completedLevelService.updateCompletedLevel(completedLevelRetried);
            }
        } else {
            CompletedLevel completedLevel = new CompletedLevel();
            completedLevel.setLevel(gameSessionManager.getLevelSelected());
            completedLevel.setScore(averageStars);
            completedLevel.setProfile(gameSessionManager.getProfile());

            completedLevelService.save(completedLevel);
            gameSessionManager.getProfile().addCompletedLevel(completedLevel);
            profileService.updateProfile(gameSessionManager.getProfile());
        }
    }


}
