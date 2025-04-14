package org.labyrinthShiftChat.service;

import org.labyrinthShiftChat.dao.GameSessionDAO;
import org.labyrinthShiftChat.model.*;
import org.labyrinthShiftChat.singleton.GameSessionManager;

public class RaTModeService {

    private MazeService mazeService=new MazeService();
    private GameSessionManager gameSessionManager=GameSessionManager.getInstance();
    private final GameSessionDAO gameSessionDAO = new GameSessionDAO();
    private ProfileService profileService= new ProfileService();


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

    public void manageSaveCompletedRaT(int minimazeComplated) {
        int previous_record;
        if(gameSessionManager.getProfile().getRecordRat()!=null){
            previous_record=gameSessionManager.getProfile().getRecordRat();
        }else{
            previous_record=0;
        }

        if (previous_record<minimazeComplated){
            gameSessionManager.getProfile().setRecordRat(minimazeComplated);
            profileService.updateProfile(gameSessionManager.getProfile());
        }

    }

}
