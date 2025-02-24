package org.example.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.model.Level;
import org.example.model.Profile;
import org.example.service.GameService;
import org.example.service.LevelService;
import org.example.service.ProfileService;
import org.example.singleton.GameSessionManager;


import java.util.HashMap;

@NoArgsConstructor
@AllArgsConstructor
public class GameController {

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