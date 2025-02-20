package org.example;

import org.example.controller.GameController;
import org.example.controller.GameSessionController;

public class Main {
    public static void main(String[] args) {

        GameController gameController = new GameController();

        GameSessionController gameSessionController = new GameSessionController();

        gameController.startGame();
        gameSessionController.startNewGameSession();
    }
}
