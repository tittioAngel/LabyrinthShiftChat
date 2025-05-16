package org.labyrinthShiftChat.controller;

import lombok.NoArgsConstructor;
import org.labyrinthShiftChat.model.*;
import org.labyrinthShiftChat.model.tiles.MazeComponent;
import org.labyrinthShiftChat.model.tiles.common.ExitTile;
import org.labyrinthShiftChat.service.*;
import org.labyrinthShiftChat.singleton.GameSessionManager;
import org.labyrinthShiftChat.util.controls.NoRotationStrategy;
import org.labyrinthShiftChat.util.controls.RotatingControls;
import org.labyrinthShiftChat.util.TimerStoryMode;
import org.labyrinthShiftChat.util.controls.RotationStrategy;
import org.labyrinthShiftChat.view.GamePlayView;
import org.labyrinthShiftChat.view.ProfileView;
import org.labyrinthShiftChat.view.StoryModeView;

import java.util.ArrayList;
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

    private final RotationStrategy noRotationStrategy = new NoRotationStrategy();

    private static final int TOTAL_MINIMAZES = 3;
    private static final long TIME_LIMIT_MILLIS = 60 * 1000;

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

            gameService.createMazeInGameSession(levelSelected.getDifficultyLevel(), GameMode.STORY_MODE);

            gamePlayView.print("\n🌀 Inizio Minimaze " + (miniMazeCompleted + 1) + " di " + TOTAL_MINIMAZES);
            gameService.previewMaze();
            storyModeView.print("⏳ Previsualizzazione terminata, il gioco sta per iniziare...");

            TimerStoryMode timer = new TimerStoryMode(TIME_LIMIT_MILLIS, gameSessionManager.getGameSession().getPlayer().getSpeed());
            int stars = 0;
            boolean finished = false;

            while (!finished) {
                stars = playLimitedView(timer);

                if (stars == -1) {
                    gamePlayView.print("\n🌀 Inizio Minimaze " + (miniMazeCompleted + 1) + " di " + TOTAL_MINIMAZES);
                    gameService.previewMaze();
                    storyModeView.print("⏳ Previsualizzazione terminata, il gioco sta per iniziare...");
                    timer = new TimerStoryMode(TIME_LIMIT_MILLIS, gameSessionManager.getGameSession().getPlayer().getSpeed());
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

    public int playLimitedView(TimerStoryMode timer) {

        if (timer.isTimeOver()) {
            storyModeView.print("\n⏳ Tempo scaduto!");
            gameService.regenerateMazeInGameSession(gameSessionManager.getGameSession().getMaze().getDifficulty(), GameMode.STORY_MODE);
            return -1;
        }

        char[][] grid = mazeService.createLimitedView(
                gameSessionManager.getGameSession().getMaze(),
                gameSessionManager.getGameSession().getCurrentTile().getX(),
                gameSessionManager.getGameSession().getCurrentTile().getY());

        gamePlayView.showMiniMaze(grid, false);
        storyModeView.print("\n⏳ Tempo rimasto: " + timer.getRemainingTimeSeconds() + " secondi velocità Giocatore: " + gameSessionManager.getGameSession().getPlayer().getSpeed());

        String direction = gamePlayView.readString("➡️ Inserisci la direzione (WASD per muoverti, Q per uscire): ").toUpperCase();
        List<String> possibleDirections = new ArrayList<>(List.of("W", "A", "S", "D"));

        if (direction.equals("Q")) {
            storyModeView.print("❌ Hai abbandonato la partita.");
            gameService.stopGame();
            return 0;
        } else if (!possibleDirections.contains(direction)) {
            storyModeView.print("❌ Direzione non corretta.");
            return 0;
        }

        RotatingControls.Direction inputDir = RotatingControls.convertInputToDirection(direction);
        RotatingControls.Direction mappedDir = noRotationStrategy.mapInput(inputDir);
        return manageDirectionSelected(mappedDir, timer);
    }

    public int manageDirectionSelected(RotatingControls.Direction direction, TimerStoryMode timer) {
        if (direction != null) {
            Tile newTile = playerService.movePlayerOnNewTile(direction);
            if (newTile != null) {
                MazeComponent mazeComponent = mazeService.findMazeComponentByTile(newTile);
                if (mazeComponent instanceof ExitTile) {
                    return scoringService.computeStars(timer.getTotalTimeSeconds());
                } else {
                    tileService.checkTileEffects();

                    if (gameSessionManager.getGameSession().getPlayer().isShowAllMaze()) {
                        gameService.previewMaze();
                        timer.addTimeSeconds(gameSessionManager.getGameSession().getMaze().getDifficulty().getPreviewTime());
                    }
                }
            }
        }

        return 0;
    }

    public void manageEndLevel(int totalStars) {
        int averageStars = totalStars / TOTAL_MINIMAZES;
        storyModeService.manageSaveCompletedLevel(averageStars);
        storyModeView.print("\n🏆 **Complimenti! Hai completato tutti i minimaze del livello.** 🏆");
        storyModeView.print("⭐ Punteggio finale medio: " + averageStars + " stelle.");
        gameSessionManager.resetSession();
    }
}
