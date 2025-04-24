package org.labyrinthShiftChat.service;

import org.labyrinthShiftChat.dao.GameSessionDAO;
import org.labyrinthShiftChat.model.*;
import org.labyrinthShiftChat.singleton.GameSessionManager;

public class RaTModeService {

    private final MazeService mazeService = new MazeService();
    private final GameSessionManager gameSessionManager = GameSessionManager.getInstance();
    private final GameSessionDAO gameSessionDAO = new GameSessionDAO();
    private final ProfileService profileService = new ProfileService();


    public RaTModeService() {}

    public void createOrRegenerateMazeInGameSession(boolean isRegeneration, DifficultyLevel difficultyLevel) {
        System.out.println(isRegeneration ? "🔄 Rigenerazione del minimaze in corso..." : "");

        Maze maze = mazeService.generateRandomMaze(difficultyLevel);
        Tile startTile = mazeService.getStartTile(maze);

        GameSession gameSession;
        if (isRegeneration) {
            gameSession = gameSessionManager.getGameSession();
            gameSession.getPlayer().setPosition(startTile.getX(), startTile.getY());
            gameSession.setMaze(maze);
            gameSession.setCurrentTile(startTile);
            gameSession.setTimeRemaining(60);
            gameSession.setGameMode(GameMode.RAT_MODE);
            gameSessionDAO.update(gameSession);

            System.out.println("✅ Nuovo minimaze pronto!");

        } else {
            gameSession = new GameSession(maze, startTile, 60);
            saveGameSession(gameSession);
            gameSessionManager.setGameSession(gameSession);
        }
    }

    public void saveGameSession(GameSession gameSession) {
        gameSessionDAO.save(gameSession);
    }

    public void manageSaveCompletedRaT(DifficultyLevel difficultyLevel, int miniMazeCompleted) {
        Profile profile = gameSessionManager.getProfile();
        int previousRecord;

        switch (difficultyLevel) {
            case EASY:
                previousRecord = (profile.getRecordRatEasy() != null) ? profile.getRecordRatEasy() : 0;
                if (miniMazeCompleted > previousRecord) {
                    profile.setRecordRatEasy(miniMazeCompleted);
                }
                break;

            case MEDIUM:
                previousRecord = (profile.getRecordRatMedium() != null) ? profile.getRecordRatMedium() : 0;
                if (miniMazeCompleted > previousRecord) {
                    profile.setRecordRatMedium(miniMazeCompleted);
                }
                break;

            case HARD:
                previousRecord = (profile.getRecordRatHard() != null) ? profile.getRecordRatHard() : 0;
                if (miniMazeCompleted > previousRecord) {
                    profile.setRecordRatHard(miniMazeCompleted);
                }
                break;
        }

        profileService.updateProfile(profile);
    }

}
