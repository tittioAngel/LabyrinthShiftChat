package org.example;

import org.example.controller.GameController;
import org.example.controller.GameSessionController;
import org.example.singleton.GameSessionManager;
import org.example.view.UserInterface;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        GameController gameController = new GameController();
        GameSessionController gameSessionController = new GameSessionController();

        UserInterface ui = new UserInterface(gameController, gameSessionController);
        ui.start();
    }
}
