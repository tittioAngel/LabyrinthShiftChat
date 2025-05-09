package org.labyrinthShiftChat.controller;

import org.labyrinthShiftChat.model.DifficultyLevel;
import org.labyrinthShiftChat.model.GameMode;
import org.labyrinthShiftChat.model.Tile;
import org.labyrinthShiftChat.model.tiles.MazeComponent;
import org.labyrinthShiftChat.model.tiles.common.ExitTile;
import org.labyrinthShiftChat.service.*;
import org.labyrinthShiftChat.singleton.GameSessionManager;
import org.labyrinthShiftChat.util.TimerRaTMode;
import org.labyrinthShiftChat.util.RotatingControls;
import org.labyrinthShiftChat.view.GamePlayView;
import org.labyrinthShiftChat.view.ProfileView;
import org.labyrinthShiftChat.view.RaTModeView;

public class RaTModeController {

    private final GameSessionManager gameSessionManager = GameSessionManager.getInstance();

    private final GameService gameService = new GameService();
    private final MazeService mazeService = new MazeService();
    private final RaTModeService raTModeService = new RaTModeService();
    private final PlayerService playerService = new PlayerService();
    private final TileService tileService = new TileService();

    private final RaTModeView raTModeView = new RaTModeView();
    private final ProfileView profileView = new ProfileView();
    private final GamePlayView gamePlayView = new GamePlayView();


    public void startRATMode() throws InterruptedException {

        boolean ratMode = true;
        DifficultyLevel difficultyLevel;

        while (ratMode) {

            raTModeView.showInfoRATMode();
            profileView.showRecordProfile(gameSessionManager.getProfile());
            raTModeView.showRaTModeMenu();

            int input;
            do {
                input = raTModeView.readIntInput("👉 Scelta: ");

                if (input < 1 || input > 4) {
                    raTModeView.print("⚠️ Scelta non valida. Riprova.");
                }
            } while (input < 1 || input > 4);

            switch (input) {
                case 1 -> {
                    difficultyLevel = chooseDifficulty();
                    managePlayGame(difficultyLevel);
                }
                case 2 -> gameService.stopGame();
                case 3 -> ratMode = false;
                default -> raTModeView.print("\n⚠️ Scelta non valida. Riprova.");
            }
        }

    }

    public void managePlayGame(DifficultyLevel difficultyLevel) throws InterruptedException {
        int miniMazeCompleted = playGame(difficultyLevel);
        manageEndGame(difficultyLevel, miniMazeCompleted);
    }

    public DifficultyLevel chooseDifficulty() {
        raTModeView.showDifficultyGame();

        int input;
        do {
            input = raTModeView.readIntInput("👉 Scelta: ");

            if (input < 1 || input > 5) {
                raTModeView.print("⚠️ Scelta non valida. Riprova.");
            }
        } while (input < 1 || input > 4);

        return DifficultyLevel.values()[input-1];
    }

    public int playGame(DifficultyLevel difficultyLevel) throws InterruptedException {
        int miniMazeCompleted = 0;

        RotatingControls rotatingControls = new RotatingControls();

        raTModeView.print("\n🎮 Difficoltà selezionata: " + difficultyLevel);
        raTModeView.print("📜 Tempo totale disponibile: " + difficultyLevel.getRatTime() + " secondi");

        TimerRaTMode timerRaTMode = new TimerRaTMode(difficultyLevel.getRatTime());

        while (!timerRaTMode.isTimeOver()) {

            timerRaTMode.pause();

            gameService.createMazeInGameSession(difficultyLevel, GameMode.RAT_MODE);
            gameService.previewMaze();
            raTModeView.print("⏳ Previsualizzazione terminata! \nIl gioco inizia ora con la visione limitata!");

            rotatingControls.rotateRandom();

            raTModeView.print("🧭 I comandi sono stati rimescolati! Occhio alla bussola!");
            raTModeView.getMappedControlsInfo(rotatingControls);

            timerRaTMode.resume();

            boolean resolve = playLimitedView(timerRaTMode, difficultyLevel, rotatingControls);

            if (resolve) {
                timerRaTMode.pause();

                miniMazeCompleted++;
                raTModeView.print("\n🏆 Hai completato il MiniMaze!");
                Thread.sleep(3000);

                timerRaTMode.resume();
            }

        }

        rotatingControls.resetRotation();

        return miniMazeCompleted;
    }

    public boolean playLimitedView(TimerRaTMode timerRaTMode, DifficultyLevel difficultyLevel, RotatingControls rotatingControls) {

        int totalMovements = difficultyLevel.getRatMovements();

        while (totalMovements > 0 && !timerRaTMode.isTimeOver()) {
            char [][] grid = mazeService.createLimitedView(gameSessionManager.getGameSession().getMaze(),
                    gameSessionManager.getGameSession().getCurrentTile().getX(),
                    gameSessionManager.getGameSession().getCurrentTile().getY());

            gamePlayView.showMiniMaze(grid, false);

            raTModeView.printTimeBar(timerRaTMode.getRemainingTime(), difficultyLevel.getRatTime() * 1000L);

            raTModeView.print("\nMovimenti totali disponibili: " + totalMovements + "/" + difficultyLevel.getRatMovements());
            raTModeView.print("\nVelocità Giocatore: "+ gameSessionManager.getGameSession().getPlayer().getSpeed());

            String direction = gamePlayView.readString("➡️ Inserisci la direzione (WASD per muoverti, Q per uscire): ");

            totalMovements --;

            RotatingControls.Direction inputDir = RotatingControls.convertInputToDirection(direction);

            if (inputDir == null) {
                gamePlayView.print("❌ Input non valido.");
                continue;
            }

            RotatingControls.Direction actualDir = rotatingControls.mapInput(inputDir);
            gamePlayView.print("🔄 Input '" + direction + "' mappato su direzione effettiva: " + actualDir);

            Tile newTile = playerService.movePlayerOnNewTile(actualDir);

            if (newTile != null) {
                MazeComponent mazeComponent = mazeService.findMazeComponentByTile(newTile);
                if (mazeComponent instanceof ExitTile) {
                    return true;
                } else {
                    tileService.checkTileEffects();

                    if (gameSessionManager.getGameSession().getPlayer().isShowAllMaze()) {
                        timerRaTMode.pause();
                        gameService.previewMaze();
                        timerRaTMode.resume();
                    }
                }
            }

        }

        return false;
    }

    public void manageEndGame(DifficultyLevel difficultyLevel, int minimazeCompelted) {

        if (minimazeCompelted > 0) {
            raTModeService.manageSaveCompletedRaT(difficultyLevel, minimazeCompelted);
            raTModeView.print("\n🏆 **Complimenti! Hai completato la modalità!** 🏆");
            raTModeView.print("⭐ Hai superato " + minimazeCompelted + " MINIMAZE in difficoltà " + difficultyLevel.getDifficultyName() );
        } else {
            raTModeView.print("❌ Purtroppo non sei riuscito a completare nessun MINIMAZE!");
        }


        gameSessionManager.resetSession();
    }
}
