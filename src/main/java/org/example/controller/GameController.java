package org.example.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.model.GameMode;
import org.example.model.Profile;
import org.example.service.GameService;
import org.example.service.ProfileService;
import org.example.singleton.GameSessionManager;
import org.example.util.MenuUtil;


import java.util.HashMap;

@NoArgsConstructor
@AllArgsConstructor
public class GameController {

    private GameService gameService = new GameService();
    private ProfileService profileService = new ProfileService();
    private MenuUtil menuUtil = new MenuUtil();
    private GameSessionManager gameSessionManager = GameSessionManager.getInstance();

    public void startGame() {
        System.out.println("🎮 Benvenuto in LabyrinthShiftChat! 🎮");

        int i = 0;
        while (!gameSessionManager.isLoggedIn()) {
            userLogin(i == 0);
            i++;
        }

        while (gameSessionManager.getGameModeSelected() == null) {
            int input = menuUtil.showModeMenu();
            if (input >= 1 && input <= GameMode.values().length) {
                gameSessionManager.setGameModeSelected(GameMode.values()[input - 1]);
            } else if (input == GameMode.values().length + 1) {
                gameService.stopGame();
            } else {
                System.out.println("⚠️ Scelta non valida. Riprova.\n");
            }
        }

        manageSelectedMode(gameSessionManager.getGameModeSelected());
    }

    public void userLogin(boolean isFirst) {
        int input = menuUtil.showStartGameMenu(isFirst);
        switch (input) {
            case 1:
                manageRetrieveProfile();
                break;
            case 2:
                manageCreateProfile();
                break;
            case 3:
                gameService.stopGame();
                break;
            default:
                System.out.println("\n⚠️ Scelta non valida. Riprova.");
        }
    }

    private void manageRetrieveProfile() {
        final HashMap<String, String> credentials = menuUtil.showCredentialsMenu();
        Profile profile = profileService.profileLogin(credentials.get("username"), credentials.get("password"));
        if (profile != null) {
            gameSessionManager.setProfile(profile);
        }
    }

    private void manageCreateProfile() {
        final HashMap<String, String> credentials = menuUtil.showCredentialsMenu();
        Profile profile = profileService.createProfile(credentials.get("username"), credentials.get("password"));
        if (profile != null) {
            gameSessionManager.setProfile(profile);
        }
    }

    public void manageSelectedMode(GameMode gameMode) {
        switch (gameMode) {
            case STORY_MODE:
                playStoryMode();
                break;
            default:
                System.out.println("\n⚠️ Scelta non valida. Riprova.");
        }
    }

    public void playStoryMode() {
        menuUtil.showUserProfile(gameSessionManager.getProfile());
        int input = menuUtil.showStoryModeMenu(gameSessionManager.getProfile());

        switch (input) {
            case 1:
                gameSessionManager.setLevelSelected(gameSessionManager.getProfile().getCompletedLevels().size() + 1);
                break;
            case 2:
                manageRetryLevel();
                break;
            default:
                System.out.println("\n⚠️ Scelta non valida. Riprova.");
        }
    }

    public void manageRetryLevel() {
        int input = menuUtil.showRetryLevelMenu();
        if (input == 0) {
            gameService.stopGame();
        }
        gameSessionManager.setLevelSelected(input);
    }
}