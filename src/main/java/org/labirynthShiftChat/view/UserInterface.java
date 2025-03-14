package org.labirynthShiftChat.view;

import lombok.Getter;
import lombok.Setter;
import org.labirynthShiftChat.controller.GameControllerCopy;
import org.labirynthShiftChat.controller.GameSessionController;
import org.labirynthShiftChat.model.*;
import org.labirynthShiftChat.singleton.GameSessionManager;

import java.util.*;

@Getter
@Setter
public class UserInterface {
    private final GameControllerCopy gameController;
    private final GameSessionController gameSessionController;
    private final GameSessionManager gameSessionManager = GameSessionManager.getInstance();
    private final Scanner scanner;
    private static final int TOTAL_MINIMAZES = 3;

    public UserInterface(GameControllerCopy gameController, GameSessionController gameSessionController) {
        this.gameController = gameController;
        this.gameSessionController = gameSessionController;
        this.scanner = new Scanner(System.in);
    }

    public void start() throws InterruptedException {


        if (gameSessionManager.isLevelSelected()) {
            Level level = gameSessionManager.getLevelSelected();
            int miniMazeCompleted = 0;
            int totalStars = 0;

            System.out.println("\n🎮 Livello selezionato: " + level.getName());

            while (miniMazeCompleted < TOTAL_MINIMAZES) {
                gameSessionController.createGameSession();

                System.out.println("\n🌀 Inizio Minimaze " + (miniMazeCompleted + 1) + " di " + TOTAL_MINIMAZES);

                gameSessionController.showMiniMazePreview();

                int stars = managePlayerMovement();
                totalStars += stars;

                System.out.println("\n🏆 Hai completato il MiniMaze " + (miniMazeCompleted + 1) + " con punteggio : " + stars + "/3");

                Thread.sleep(3000);

                miniMazeCompleted++;
            }

            manageEndLevel(totalStars);
        }
    }





    public int managePlayerMovement() {
        System.out.println("✅ Il gioco inizia ora con la visione limitata!");

        long startTime = System.currentTimeMillis(); // Avvio del timer locale
        long timeLimit = 60 * 1000; // 60 secondi in millisecondi

        while (true) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            long remainingTime = (timeLimit - elapsedTime) / 1000;

            // Se il tempo è scaduto, rigeneriamo il minimaze e resettare il timer
            if (remainingTime <= 0) {
                System.out.println("⏳ Tempo scaduto! Rigenerazione del minimaze...");
                gameSessionController.regenerateMiniMaze();
                // Reset del timer per il nuovo tentativo sullo stesso minimaze
                startTime = System.currentTimeMillis();
                continue; // Riprende il ciclo senza uscire
            }

            gameSessionController.showLimitedMiniMazeView();
            System.out.println("\n⏳ Tempo rimasto: " + remainingTime + " secondi.");
            System.out.print("➡️ Inserisci la direzione (WASD per muoverti, Q per uscire): ");

            String direction = scanner.nextLine().toUpperCase();

            int stars = gameSessionController.manageDirectionSelected(direction, startTime);

            if (stars != 0) {
                return stars;
            }
        }
    }

    public void manageEndLevel(int totalStars) {

        int averageStars = totalStars / TOTAL_MINIMAZES;
        //Devo controllare se il livello è gia stato completato
        gameSessionController.saveCompletedLevel(averageStars);
        System.out.println("\n🏆 **Complimenti! Hai completato tutti i minimaze del livello.** 🏆");
        System.out.println("⭐ Punteggio finale medio: " + averageStars + " stelle.");

        gameSessionManager.resetSession();
    }
}
