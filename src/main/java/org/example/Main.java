package org.example;

import org.example.controller.GameController;
import org.example.controller.GameSessionController;
import org.example.controller.PlayerController;
import org.example.singleton.GameSessionManager;

public class Main {
    public static void main(String[] args) {

        GameController gameController = new GameController();
        GameSessionController gameSessionController = new GameSessionController();
        PlayerController playerController = new PlayerController();
        GameSessionManager gameSessionManager = GameSessionManager.getInstance();

        while (true) {
            gameController.startGame();
            if (gameSessionManager.getLevelSelected() != 0) {
                gameSessionController.startNewGameSession();
            }
        }
    }
}
