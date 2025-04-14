package org.labyrinthShiftChat.controller;

import lombok.NoArgsConstructor;
import org.labyrinthShiftChat.model.CompletedLevel;
import org.labyrinthShiftChat.model.Level;
import org.labyrinthShiftChat.model.Maze;
import org.labyrinthShiftChat.model.Tile;
import org.labyrinthShiftChat.model.tiles.ExitTile;
import org.labyrinthShiftChat.service.*;
import org.labyrinthShiftChat.singleton.GameSessionManager;
import org.labyrinthShiftChat.view.GamePlayView;
import org.labyrinthShiftChat.view.ProfileView;
import org.labyrinthShiftChat.view.StoryModeView;

import java.util.List;

@NoArgsConstructor
public class StoryModeController {

    private final GameSessionManager gameSessionManager = GameSessionManager.getInstance();

    private final LevelService levelService = new LevelService();
    private final GameService gameService = new GameService();
    private final MazeService mazeService = new MazeService();
    private final StoryModeService storyModeService = new StoryModeService();
    private final PlayerService playerService = new PlayerService();
    private final ScoringService scoringService = new ScoringService();
    private final TileService tileService = new TileService();

    private final StoryModeView storyModeView = new StoryModeView();
    private final ProfileView profileView = new ProfileView();
    private final GamePlayView gamePlayView = new GamePlayView();

    private static final int TOTAL_MINIMAZES = 3;

    public void startStoryMode() throws InterruptedException {

        boolean stayInStoryMode = true;

        int levelSelected = 0;

        while (stayInStoryMode) {
            profileView.showProfileInfo(gameSessionManager.getProfile());

            storyModeView.showStoryModeMenu();

            int input;
            do {
                input = storyModeView.readIntInput("👉 Scelta: ");

                if (input < 1 || input > 4) {
                    storyModeView.print("⚠️ Scelta non valida. Riprova.");
                }
            } while (input < 1 || input > 4);

            switch (input) {
                case 1 -> {
                    levelSelected = gameSessionManager.getProfile().getCompletedLevels().size() + 1;
                    gameSessionManager.setLevelSelected(levelService.findLevelByNumber(levelSelected));
                    managePlayLevel();
                }
                case 2 -> {
                    levelSelected = showRetryLevelMenu();
                    if (levelSelected > 0) {
                        managePlayLevel();
                    }
                }
                case 3 -> gameService.stopGame();
                case 4 -> stayInStoryMode = false;
                default -> storyModeView.print("\n⚠️ Scelta non valida. Riprova.");
            }
        }

    }

    public int showRetryLevelMenu() {
        List<CompletedLevel> completedLevels = gameSessionManager.getProfile().getCompletedLevels();

        if (completedLevels.isEmpty()) {
            storyModeView.print("\n⚠️ Non hai ancora completato alcun livello da riprovare.");
            return -1;
        }

        storyModeView.showRetryMenu();

        int levelSelected;
        do {
            levelSelected = storyModeView.readIntInput("👉 Inserisci il numero del livello: ");

            if (levelSelected < 1 || levelSelected > completedLevels.size() + 2) {
                storyModeView.print("❌ Scelta non valida. Riprova.");
            }
        } while (levelSelected < 1 || levelSelected > completedLevels.size() + 2);

        if (levelSelected == completedLevels.size() + 2) {
            gameService.stopGame();
        } else if (levelSelected == completedLevels.size() + 1) {
            return 0;
        } else {
            gameSessionManager.setLevelSelected(levelService.findLevelByNumber(levelSelected));
            return levelSelected;
        }

        return 0;
    }

    public void managePlayLevel() throws InterruptedException {
        int starsReturned = playLevel(gameSessionManager.getLevelSelected());
        manageEndLevel(starsReturned);
    }

    public int playLevel(Level levelSelected) throws InterruptedException {
        int miniMazeCompleted = 0;
        int totalStars = 0;

        storyModeView.print("\n🎮 Livello selezionato: " + levelSelected.getName());

        while (miniMazeCompleted < TOTAL_MINIMAZES) {

            storyModeService.createOrRegenerateMazeInGameSession(false);

            previewMaze(miniMazeCompleted);

            long startTime = System.currentTimeMillis();
            long timeLimit = 60 * 1000;
            int stars = 0;
            boolean finished = false;
            while (!finished) {

                stars = playLimitedView(startTime, timeLimit);

                if (stars == -1) {
                    previewMaze(miniMazeCompleted);
                    startTime = System.currentTimeMillis();
                } else if (stars != 0) {
                    finished = true;
                }
            }

            totalStars += stars;

            storyModeView.print("\n🏆 Hai completato il MiniMaze " + (miniMazeCompleted + 1) + " con punteggio: " + stars + "/3");

            Thread.sleep(3000);

            miniMazeCompleted++;
        }

        return totalStars;


    }

    public void previewMaze(int miniMazeCompleted) {
        gamePlayView.print("\n🌀 Inizio Minimaze " + (miniMazeCompleted + 1) + " di " + TOTAL_MINIMAZES);
        Maze maze = gameSessionManager.getGameSession().getMaze();

        char [][] grid = mazeService.createPreviewMiniMaze(maze);

        gamePlayView.showMiniMaze(grid, true);

        try {
            Thread.sleep(maze.getDifficulty().getPreviewTime() * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        storyModeView.print("⏳ Previsualizzazione terminata, il gioco sta per iniziare...");
    }

    public int playLimitedView(long startTime, long timeLimit) {
        long elapsedTime;

        if(!gameSessionManager.getGameSession().getPlayer().resetSpeed()){
            elapsedTime = System.currentTimeMillis() - startTime;
        }else{
             elapsedTime = (long) ((System.currentTimeMillis()*(1/gameSessionManager.getGameSession().getPlayer().getSpeed()))- startTime);
        }

        long remainingTime = (timeLimit - elapsedTime) / 1000;

        char[][]grid;

        // Se il tempo è scaduto, rigeneriamo il minimaze e resettare il timer
        if (remainingTime <= 0) {
            storyModeView.print("\n⏳ Tempo scaduto!");
            storyModeService.createOrRegenerateMazeInGameSession(true);

            return -1;
        }

        grid = mazeService.createLimitedView(gameSessionManager.getGameSession().getMaze(),
                gameSessionManager.getGameSession().getCurrentTile().getX(),
                gameSessionManager.getGameSession().getCurrentTile().getY());

        gamePlayView.showMiniMaze(grid, false);

        storyModeView.print("\n⏳ Tempo rimasto: " + remainingTime + " secondi velocità Giocatore: "+ gameSessionManager.getGameSession().getPlayer().getSpeed());

        String direction = gamePlayView.readString("➡️ Inserisci la direzione (WASD per muoverti, Q per uscire): ");

        return manageDirectionSelected(direction.toUpperCase(), startTime);

    }

    public int manageDirectionSelected(String direction, long startTime) {
        if (direction.equals("Q")) {
            storyModeView.print("❌ Hai abbandonato la partita.");
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

    public void manageEndLevel(int totalStars) {

        int averageStars = totalStars / TOTAL_MINIMAZES;
        //Devo controllare se il livello è gia stato completato
        storyModeService.manageSaveCompletedLevel(averageStars);
        storyModeView.print("\n🏆 **Complimenti! Hai completato tutti i minimaze del livello.** 🏆");
        storyModeView.print("⭐ Punteggio finale medio: " + averageStars + " stelle.");

        gameSessionManager.resetSession();
    }
}
