package org.labyrinthShiftChat.controller;

import org.labyrinthShiftChat.model.DifficultyLevel;
import org.labyrinthShiftChat.model.Maze;
import org.labyrinthShiftChat.model.Tile;
import org.labyrinthShiftChat.model.tiles.ExitTile;
import org.labyrinthShiftChat.service.*;
import org.labyrinthShiftChat.singleton.GameSessionManager;
import org.labyrinthShiftChat.view.GamePlayView;
import org.labyrinthShiftChat.view.ProfileView;
import org.labyrinthShiftChat.view.RaTModeView;

public class RaTModeController {

    private final GameSessionManager gameSessionManager = GameSessionManager.getInstance();

    private final LevelService levelService = new LevelService();
    private final GameService gameService = new GameService();
    private final MazeService mazeService = new MazeService();
    private final RaTModeService raTModeService = new RaTModeService();
    private final PlayerService playerService = new PlayerService();
    private final ScoringService scoringService = new ScoringService();
    private final TileService tileService = new TileService();

    private final RaTModeView raTModeView = new RaTModeView();
    private final ProfileView profileView = new ProfileView();
    private final GamePlayView gamePlayView = new GamePlayView();


    public void startRATMode() throws InterruptedException {

        boolean ratMode = true;
        DifficultyLevel difficultyLevel;
        int levelSelected = 0;

        while (ratMode) {
            //profileView.showProfileInfo(gameSessionManager.getProfile());

            raTModeView.showInfoRATMode();
            profileView.showrecordProfile(gameSessionManager.getProfile());
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
                    //Scegliamo la difficoltà del livello da giocare
                    difficultyLevel=chooseDifficulty();

                    managePlayGame(difficultyLevel);
                }
                case 2 -> gameService.stopGame();
                case 3 -> ratMode = false;
                default -> raTModeView.print("\n⚠️ Scelta non valida. Riprova.");
            }
        }

    }

    public void managePlayGame(DifficultyLevel difficultyLevel) throws InterruptedException {
        int minimazeCompleted = playGame(difficultyLevel);
        manageEndGame(minimazeCompleted);
    }

    public DifficultyLevel chooseDifficulty() throws InterruptedException {

        int input;
        //Gestire l'inizio del gioco
        raTModeView.showDifficultyGame();
        input=raTModeView.readIntInput("👉 Scelta: ");

        DifficultyLevel difficultyLevel= DifficultyLevel.values()[input-1];


        return difficultyLevel;

    }

    public int playGame(DifficultyLevel difficultyLevel) throws InterruptedException {
        int miniMazeCompleted = 0;
        raTModeView.print("\n🎮 Difficoltà selezionata: " + difficultyLevel);


        long startPlayTime = System.currentTimeMillis();
        long pausedTime = 0;
        long accomulatedTime = 0;
        long totalLimit = 2 * 60 * 1000; // 2 minuti in ms

        while ((System.currentTimeMillis()-startPlayTime-pausedTime) <totalLimit) {

            raTModeService.createOrRegenerateMazeInGameSession(false,difficultyLevel);
            raTModeView.print("Tempo trascorso "+((System.currentTimeMillis() - startPlayTime - pausedTime)/1000)+" secondi");

            long previewStart = System.currentTimeMillis();
            previewMaze();
            long previewEnd = System.currentTimeMillis();
            pausedTime += (previewEnd - previewStart); // somma durata della preview

            long startTime = System.currentTimeMillis();// Avvio del timer locale
            long timeLimit = 60 * 1000; // 60 secondi in millisecondi

            int stars = 0;


            boolean finished = false;
            while (!finished) {

                stars = playLimitedView(startTime, timeLimit, difficultyLevel);

                if (stars == -1) {
                    previewStart = System.currentTimeMillis();
                    previewMaze();
                    previewEnd = System.currentTimeMillis();
                    pausedTime += (previewEnd - previewStart);

                    startTime = System.currentTimeMillis();
                } else if (stars != 0) {
                    finished = true;
                }
            }



            raTModeView.print("\n🏆 Hai completato il MiniMaze ");

            Thread.sleep(3000);

            if((System.currentTimeMillis()-startPlayTime-pausedTime) <totalLimit)
                miniMazeCompleted++;
        }

        return miniMazeCompleted;


    }

    public void previewMaze() {
        gamePlayView.print("\n🌀 Inizio Minimaze ");
        Maze maze = gameSessionManager.getGameSession().getMaze();

        char [][] grid = mazeService.createPreviewMiniMaze(maze);

        gamePlayView.showMiniMaze(grid, true);

        try {
            Thread.sleep(maze.getDifficulty().getPreviewTime() * 1000L); // Attesa per la previsualizzazione
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        raTModeView.print("⏳ Previsualizzazione terminata, il gioco sta per iniziare...");
    }

    public int playLimitedView(long startTime, long timeLimit, DifficultyLevel difficultyLevel) {
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
            raTModeView.print("\n⏳ Tempo scaduto!");
            raTModeService.createOrRegenerateMazeInGameSession(true,difficultyLevel);
            return -1;
        } else {
            raTModeView.print("✅ Il gioco inizia ora con la visione limitata!");
        }

        grid = mazeService.createLimitedView(gameSessionManager.getGameSession().getMaze(),
                gameSessionManager.getGameSession().getCurrentTile().getX(),
                gameSessionManager.getGameSession().getCurrentTile().getY());

        gamePlayView.showMiniMaze(grid, false);

        raTModeView.print("\n⏳ Tempo rimasto: " + remainingTime + " secondi  Velocità Giocatore: "+ gameSessionManager.getGameSession().getPlayer().getSpeed());

        String direction = gamePlayView.readString("➡️ Inserisci la direzione (WASD per muoverti, Q per uscire): ");

        return manageDirectionSelected(direction.toUpperCase(), startTime);

    }

    public int manageDirectionSelected(String direction, long startTime) {
        if (direction.equals("Q")) {
            gamePlayView.print("❌ Hai abbandonato la partita.");
            gameService.stopGame();
        }

        Tile newTile = playerService.movePlayerOnNewTile(direction);

        if (newTile != null) {
            if (newTile instanceof ExitTile) {
                return 1;
            } else {
                tileService.checkTileEffects(gameSessionManager.getGameSession(), gameSessionManager.getGameSession().getPlayer());
            }
        }

        return 0;
    }

    public void manageEndGame(int minimazeCompelted) {

        //Devo controllare se il minimazeComplated sono più del record precedente
        raTModeService.manageSaveCompletedRaT(minimazeCompelted);
        raTModeView.print("\n🏆 **Complimenti! Hai completato la modalità sfida.** 🏆");
        raTModeView.print("⭐ Hai superato " + minimazeCompelted + " Minimaze.");

        gameSessionManager.resetSession();
    }
}
