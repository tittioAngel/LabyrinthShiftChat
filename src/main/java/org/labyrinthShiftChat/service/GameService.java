package org.labyrinthShiftChat.service;

import lombok.NoArgsConstructor;
import org.labyrinthShiftChat.dao.GameSessionDAO;
import org.labyrinthShiftChat.dao.MazeComponentDAO;
import org.labyrinthShiftChat.model.*;
import org.labyrinthShiftChat.model.tiles.MazeComponent;
import org.labyrinthShiftChat.model.tiles.common.StartTile;
import org.labyrinthShiftChat.singleton.GameSessionManager;
import org.labyrinthShiftChat.view.GamePlayView;

@NoArgsConstructor
public class GameService {

    private final MazeService mazeService = new MazeService();
    private final GameSessionManager gameSessionManager = GameSessionManager.getInstance();
    private final GameSessionDAO gameSessionDAO = new GameSessionDAO();

    private final GamePlayView gamePlayView = new GamePlayView();

    public void stopGame() {
        gameSessionManager.logOut();
        System.out.println("\n👋 Grazie per aver giocato! Alla prossima! 🎮");
        System.exit(0);
    }

    public void createMazeInGameSession(DifficultyLevel difficultyLevel, GameMode gameMode) {
        generateMazeInGameSession(false, difficultyLevel, gameMode);
    }

    public void regenerateMazeInGameSession(DifficultyLevel difficultyLevel, GameMode gameMode) {
        generateMazeInGameSession(true, difficultyLevel, gameMode);
    }

    public void generateMazeInGameSession(boolean isRegeneration, DifficultyLevel difficultyLevel, GameMode gameMode) {
        System.out.println(isRegeneration ? "🔄 Rigenerazione del minimaze in corso..." : "");

        Maze maze = mazeService.generateRandomMaze(difficultyLevel);
        StartTile startTile = mazeService.getStartTile(maze);

        GameSession gameSession;
        if (isRegeneration) {
            gameSession = gameSessionManager.getGameSession();
            gameSession.getPlayer().setPosition(startTile.getTile().getX(), startTile.getTile().getY());
            gameSession.setMaze(maze);
            gameSession.setCurrentTile(startTile.getTile());
            gameSession.setTimeRemaining(60);
            gameSession.setGameMode(gameMode);
            gameSessionDAO.update(gameSession);

            System.out.println("✅ Nuovo minimaze pronto!");

        } else {
            gameSession = new GameSession(maze, startTile.getTile(), 60);
            saveGameSession(gameSession);
            gameSessionManager.setGameSession(gameSession);
        }
    }

    public void saveGameSession(GameSession gameSession) {
        gameSessionDAO.save(gameSession);
    }

    public void previewMaze() {
        Maze maze = gameSessionManager.getGameSession().getMaze();

        char [][] grid = mazeService.createPreviewMiniMaze(maze);

        gamePlayView.showMiniMaze(grid, true);

        try {
            Thread.sleep(maze.getDifficulty().getPreviewTime() * 1000L); // Attesa per la previsualizzazione
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
