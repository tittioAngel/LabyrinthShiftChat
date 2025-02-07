package org.example.controller;

import org.example.model.*;
import org.example.service.GameSessionService;
import org.example.service.MazeService;
import org.example.service.ScoringService;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class GameSessionController {
    private final GameSessionService gameSessionService = new GameSessionService();
    private final MazeService mazeService = new MazeService();
    private final ScoringService scoringService = new ScoringService();
    private boolean gameOver = false;
    private static final int TOTAL_MINIMAZES = 3;

    public void startNewGame() {
        Scanner scanner = new Scanner(System.in);

        // Selezione della difficoltà
        System.out.println("Scegli la difficoltà (EASY, MEDIUM, HARD): ");
        String difficultyInput = scanner.nextLine().toUpperCase();
        DifficultyLevel difficulty = DifficultyLevel.valueOf(difficultyInput);


        int totalStars=0;
        int minimazeCompleted = 0;
        //Entriamo in uno dei 3 minimaze
        while (minimazeCompleted < TOTAL_MINIMAZES) {
            System.out.println("\n🌀 Inizio Minimaze " + (minimazeCompleted+1) + " di " + TOTAL_MINIMAZES);
            boolean completed = false;

            while (!completed) {
            // Genera un minimaze casuale
            Maze maze = mazeService.generateRandomMaze(difficulty);

            // Recupera la posizione iniziale del giocatore
            Tile startTile = mazeService.getStartTile(maze);
            int playerX = startTile.getX();
            int playerY = startTile.getY();

            // Pre-visualizzazione
            System.out.println("🔍 Preview del minimaze");
            mazeService.displayMaze(maze);
            try {
                Thread.sleep(difficulty.getPreviewTime() * 1000); // Attesa del tempo di pre-visualizzazione
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.println("\n⏳ Tempo scaduto! Ora puoi giocare.");
            System.out.println("⏳ Ora risolvi il minimaze con WASD! Hai " + 60 + " Secondi.");

            // Avvio del gameplay con movimento del giocatore
                int stars = movePlayer(scanner, maze, playerX, playerY, difficulty);

            if (stars>0) {
                minimazeCompleted++;
                totalStars+=stars;
                System.out.println("🎉 Minimaze completato con " + stars + " stelle!");
                completed = true;
            }else
                System.out.println("❌ Tempo scaduto! Devi ricominciare il minimaze.");
            }

        }
        double averageStars = totalStars / (double) TOTAL_MINIMAZES;
        System.out.println("\n🏆 **Complimenti! Hai completato tutti i minimaze del livello.** 🏆");
        System.out.println("⭐ Punteggio finale medio: " + averageStars + " stelle.");
    }



    /**
     * Gestisce il movimento del giocatore e calcola il punteggio in stelle in base al tempo di completamento.
     * @return il numero di stelle ottenute se il minimaze è completato, altrimenti 0.
     */

    private int movePlayer(Scanner scanner, Maze maze, int playerX, int playerY, DifficultyLevel difficulty) {
        long timeLimit =60*1000; // Tempo limite in secondi
        long startTime;

        while (true) {
            startTime = System.currentTimeMillis(); // 🔄 Resetta il timer a ogni rigenerazione

            while ((System.currentTimeMillis() - startTime) < timeLimit) {

                // Mostra il labirinto con il giocatore segnato come 'G'
                mazeService.displayLimitedView(maze, playerX, playerY);

                long remainingTime = (timeLimit - (System.currentTimeMillis() - startTime)) / 1000;
                System.out.println("\n⏳ Tempo rimasto: " + remainingTime + " secondi.");
                System.out.print("➡️ Inserisci la direzione (WASD per muoverti, Q per uscire): ");
                String input = scanner.nextLine().toUpperCase();

                if (input.equals("Q")) {
                    System.out.println("❌ Hai abbandonato la partita.");
                    return 0;
                }

                int newX = playerX, newY = playerY;
                switch (input) {
                    case "W": newY--; break;
                    case "S": newY++; break;
                    case "A": newX--; break;
                    case "D": newX++; break;
                    default:
                        System.out.println("⛔ Input non valido. Usa W, A, S, D per muoverti.");
                        continue;
                }

                if (mazeService.isValidMove(maze, newX, newY)) {
                    playerX = newX;
                    playerY = newY;
                    System.out.println("✅ Ti sei mosso a: (" + playerX + ", " + playerY + ")");

                    // qui andrebbe inserito il controllo su nemici ancora da fare



                    if (mazeService.isExit(maze, playerX, playerY)) {

                        long elapsedSeconds = (System.currentTimeMillis() - startTime) / 1000;
                        int stars = scoringService.computeStars(elapsedSeconds);
                        System.out.println("🏁 Complimenti! Hai completato il minimaze!");

                        return stars;
                    }
                } else {
                    System.out.println("⛔ Movimento non valido, c'è un muro o un ostacolo!");
                }
            }

            // 🔄 Se il tempo scade, resettiamo il minimaze
            System.out.println("⏰ Tempo scaduto! Generando un nuovo minimaze...");
            return 0;
        }
    }

}
