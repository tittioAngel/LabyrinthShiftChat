package org.example.controller;

import org.example.model.*;
import org.example.service.GameSessionService;
import org.example.service.ScoringService;

import java.util.Scanner;

import  org.example.dao.LevelDAO;

public class GameSessionController {
    private final GameSessionService gameSessionService = new GameSessionService();
    private final ScoringService scoringService = new ScoringService();
    private final LevelDAO levelDAO = new LevelDAO();
    private static final int TOTAL_MINIMAZES = 3;


    public void startNewGame() {
        Scanner scanner = new Scanner(System.in);

        // ✅ creiamo il profilo del giocatore
        Profile profile= new Profile("ciao","1234");

        System.out.println("Scegli la difficoltà (EASY, MEDIUM, HARD): ");
        String difficultyInput = scanner.nextLine().toUpperCase();
        DifficultyLevel difficulty = DifficultyLevel.valueOf(difficultyInput);


        int totalStars = 0;
        int minimazeCompleted = 0;

        while (minimazeCompleted < TOTAL_MINIMAZES) {
            System.out.println("\n🌀 Inizio Minimaze " + (minimazeCompleted + 1) + " di " + TOTAL_MINIMAZES);
            GameSession gameSession = gameSessionService.startNewMinimaze(difficulty,profile);

            // Gestione locale del timer per 60 secondi
            int stars = handlePlayerMovement(scanner, gameSession);
            if (stars > 0) {
                minimazeCompleted++;
                totalStars += stars;
                System.out.println("🎉 Minimaze completato con " + stars + " stelle!");

            }
        }

        double averageStars = totalStars / (double) TOTAL_MINIMAZES;
        System.out.println("\n🏆 **Complimenti! Hai completato tutti i minimaze del livello.** 🏆");
        System.out.println("⭐ Punteggio finale medio: " + averageStars + " stelle.");
    }

    private int handlePlayerMovement(Scanner scanner, GameSession gameSession) {
        long startTime = System.currentTimeMillis(); // Avvio del timer locale
        long timeLimit = 60 * 1000; // 60 secondi in millisecondi

        while (true) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            long remainingTime = (timeLimit - elapsedTime) / 1000;

            // Se il tempo è scaduto, rigeneriamo il minimaze e resettare il timer
            if (remainingTime <= 0) {
                System.out.println("⏳ Tempo scaduto! Rigenerazione del minimaze...");
                gameSessionService.regenerateMaze(gameSession);
                // Reset del timer per il nuovo tentativo sullo stesso minimaze
                startTime = System.currentTimeMillis();
                continue; // Riprende il ciclo senza uscire
            }

            // Mostriamo la vista limitata e il tempo residuo
            gameSessionService.displayLimitedView(gameSession);
            System.out.println("\n⏳ Tempo rimasto: " + remainingTime + " secondi.");
            System.out.print("➡️ Inserisci la direzione (WASD per muoverti, Q per uscire): ");

            String input = scanner.nextLine().toUpperCase();
            if (input.equals("Q")) {
                System.out.println("❌ Hai abbandonato la partita.");
                return 0;
            }

            int moveResult = gameSessionService.movePlayer(gameSession, input);
            if (moveResult == GameSessionService.EXIT_REACHED) {
                long totalTimeSeconds = (System.currentTimeMillis() - startTime) / 1000;
                int stars = gameSessionService.computeStars(totalTimeSeconds);
                return stars;
            }
        }
    }





    public void playLevel(int levelNumber) {
        // Recupera il livello dal database (opzionale se serve per logica di gioco)
        Level level = levelDAO.retrieveLevelByNumber(levelNumber);

        // Avvia la nuova sessione di gioco (minimaze)
        GameSessionController gameSessionController = new GameSessionController();
        gameSessionController.startNewGame();
    }
}
