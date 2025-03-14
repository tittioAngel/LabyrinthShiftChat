package org.labirynthShiftChat.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.labirynthShiftChat.model.Level;
import org.labirynthShiftChat.model.Profile;
import org.labirynthShiftChat.service.GameService;
import org.labirynthShiftChat.service.LevelService;
import org.labirynthShiftChat.service.ProfileService;
import org.labirynthShiftChat.singleton.GameSessionManager;


import java.util.HashMap;

@NoArgsConstructor
@AllArgsConstructor
public class GameControllerCopy {

    private GameService gameService = new GameService();
    private ProfileService profileService = new ProfileService();
    private LevelService levelService = new LevelService();
    private GameSessionManager gameSessionManager = GameSessionManager.getInstance();

    public void manageUserLogin(HashMap<String, String> credentials) {
        Profile profile = profileService.profileLogin(credentials.get("username"), credentials.get("password"));
        if (profile != null) {
            gameSessionManager.setProfile(profile);
        }
    }

    public void manageUserSignUp(HashMap<String, String> credentials) {
        Profile profile = profileService.createProfile(credentials.get("username"), credentials.get("password"));
        if (profile != null) {
            gameSessionManager.setProfile(profile);
        }
    }

    public void stopGame() {
        gameService.stopGame();
    }

    public Level obtainLevelToPlay(int levelIdentifier) {
        return levelService.findLevelByNumber(levelIdentifier);
    }
}