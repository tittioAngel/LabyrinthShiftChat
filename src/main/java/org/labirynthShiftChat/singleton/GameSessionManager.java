package org.labirynthShiftChat.singleton;

import lombok.Getter;
import lombok.Setter;
import org.labirynthShiftChat.model.GameSession;
import org.labirynthShiftChat.model.Level;
import org.labirynthShiftChat.model.Profile;

@Getter
@Setter
public class GameSessionManager {

    private static final GameSessionManager instance = new GameSessionManager();

    private Profile profile;

    private Level levelSelected;

    private GameSession gameSession;

    private GameSessionManager() {
        this.profile = null;
        this.levelSelected = null;
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

    public boolean isLevelSelected() {
        return levelSelected != null;
    }

    public void resetSession() {
        levelSelected = null;
        gameSession = null;
    }

}