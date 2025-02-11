package org.example.singleton;

import lombok.Getter;
import lombok.Setter;
import org.example.model.Profile;

@Getter
@Setter
public class GameSessionManager {

    private static final GameSessionManager instance = new GameSessionManager();

    private Profile profile;

    private GameSessionManager() {
        this.profile = null;
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
}
