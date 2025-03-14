package org.labyrinthShiftChat.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.labyrinthShiftChat.model.Level;
import org.labyrinthShiftChat.model.Profile;
import org.labyrinthShiftChat.service.GameService;
import org.labyrinthShiftChat.service.LevelService;
import org.labyrinthShiftChat.service.ProfileService;
import org.labyrinthShiftChat.singleton.GameSessionManager;


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