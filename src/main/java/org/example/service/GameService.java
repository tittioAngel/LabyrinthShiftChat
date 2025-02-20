package org.example.service;

import lombok.NoArgsConstructor;
import org.example.singleton.GameSessionManager;

@NoArgsConstructor
public class GameService {

    private final GameSessionManager gameSessionManager = GameSessionManager.getInstance();

    public void stopGame() {
        gameSessionManager.logOut();
        System.out.println("\n👋 Grazie per aver giocato! Alla prossima! 🎮");
        System.exit(0);
    }

}
