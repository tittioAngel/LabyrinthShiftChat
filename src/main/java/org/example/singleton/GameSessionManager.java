package org.example.singleton;

import lombok.Getter;
import lombok.Setter;
import org.example.model.GameMode;
import org.example.model.GameSession;
import org.example.model.Profile;

@Getter
@Setter
public class GameSessionManager {

    private static final GameSessionManager instance = new GameSessionManager();

    private Profile profile;

    private GameMode gameModeSelected;

    private int levelSelected;

    private GameSession gameSession;

    private GameSessionManager() {
        this.profile = null;
        this.gameModeSelected = null;
        this.levelSelected = 0;
        this.gameSession = null;
    }

    public static synchronized GameSessionManager getInstance() {
        return instance;
    }

    public boolean isLoggedIn() {
        return profile != null;
    }

    public void logOut() {
        profile = null;
    }

    public boolean isGameOver() {
        return gameSession != null;
    }
}
