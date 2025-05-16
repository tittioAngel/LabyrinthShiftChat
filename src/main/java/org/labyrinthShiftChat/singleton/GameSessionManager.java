package org.labyrinthShiftChat.singleton;

import lombok.Getter;
import lombok.Setter;
import org.labyrinthShiftChat.model.GameSession;
import org.labyrinthShiftChat.model.Level;
import org.labyrinthShiftChat.model.Profile;

@Getter
@Setter
public class GameSessionManager {

    private static GameSessionManager instance;

    private Profile profile;
    private Level levelSelected;
    private GameSession gameSession;

    private GameSessionManager() {
        this.profile = null;
        this.levelSelected = null;
        this.gameSession = null;
    }

    public static synchronized GameSessionManager getInstance() {
        if (instance == null) {
            instance = new GameSessionManager();
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return profile != null;
    }

    public void logOut() {
        profile = null;
    }

    public boolean isLevelSelected() {
        return levelSelected != null;
    }

    public void resetSession() {
        levelSelected = null;
        gameSession = null;
    }
}
