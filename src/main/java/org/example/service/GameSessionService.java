package org.example.service;

import org.example.dao.*;
import org.example.model.*;
import org.example.model.entities.Adversity;
import org.example.model.tiles.ExitTile;
import org.example.model.tiles.Wall;
import org.example.singleton.GameSessionManager;


public class GameSessionService {
    private final GameSessionManager gameSessionManager = GameSessionManager.getInstance();
    private final GameSessionDAO gameSessionDAO = new GameSessionDAO();
    private final MazeService mazeService = new MazeService();
    private final ScoringService scoringService = new ScoringService();
    public static final int EXIT_REACHED = 1;

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



}
