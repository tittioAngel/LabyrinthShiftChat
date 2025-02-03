package org.example.singleton;

import org.example.service.GameSessionService;
import org.example.service.MazeService;
import org.example.service.PlayerService;

public class ServiceFactory {

    private static final ServiceFactory instance = new ServiceFactory();

    private final PlayerService playerService;
    private final MazeService mazeService;
    private final GameSessionService gameSessionService;

    private ServiceFactory() {
        this.playerService = new PlayerService();
        this.mazeService = new MazeService();
        this.gameSessionService = new GameSessionService();
    }

    public static ServiceFactory getInstance() {
        return instance;
    }

    public PlayerService getPlayerService() {
        return playerService;
    }

    public MazeService getMazeService() {
        return mazeService;
    }

    public GameSessionService getGameSessionService() {
        return gameSessionService;
    }
}
