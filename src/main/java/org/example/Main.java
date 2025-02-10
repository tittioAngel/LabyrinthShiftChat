package org.example;

import org.example.controller.GameController;

public class Main {
    public static void main(String[] args) {

        GameController gameController = new GameController();
        gameController.startGame();
        //GameSessionController gameSessionController = new GameSessionController();
        //gameSessionController.startNewGame();
    }
}
