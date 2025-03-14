package org.labirynthShiftChat;

import org.labirynthShiftChat.controller.GameController;
import org.labirynthShiftChat.controller.GameControllerCopy;
import org.labirynthShiftChat.controller.GameSessionController;
import org.labirynthShiftChat.view.UserInterface;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        GameController gameController = new GameController();

        while (true) {
            gameController.startGame();
        }
//        GameControllerCopy gameController = new GameControllerCopy();
//        GameSessionController gameSessionController = new GameSessionController();
//
//        UserInterface ui = new UserInterface(gameController, gameSessionController);
//        while (true) {
//            ui.start();
//        }
    }
}
