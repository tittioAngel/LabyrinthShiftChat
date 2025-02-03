package org.example;

import org.example.controller.GameSessionController;

public class Main {
    public static void main(String[] args) {
        System.out.println("🎮 Benvenuto in LabyrinthShiftChat!");

        // Avvio della partita con il GameSessionController
        GameSessionController gameSessionController = new GameSessionController();
        gameSessionController.startNewGame();
    }
}
