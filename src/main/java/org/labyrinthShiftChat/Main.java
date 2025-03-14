package org.labyrinthShiftChat;

import org.labyrinthShiftChat.controller.GameController;

public class Main {
    public static void main(String[] args) {
        GameController gameController = new GameController();

        gameController.startGame();
//        GameControllerCopy gameController = new GameControllerCopy();
//        GameSessionController gameSessionController = new GameSessionController();
//
//        UserInterface ui = new UserInterface(gameController, gameSessionController);
//        while (true) {
//            ui.start();
//        }
    }
}
