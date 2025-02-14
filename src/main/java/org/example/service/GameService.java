package org.example.service;

import lombok.NoArgsConstructor;
import org.example.model.Profile;
import org.example.singleton.GameSessionManager;
import org.example.util.MenuUtil;

import java.util.HashMap;

@NoArgsConstructor
public class GameService {

    private final MenuUtil menuUtil = new MenuUtil();
    private final ProfileService profileService = new ProfileService();
    private final LevelService levelService = new LevelService();

    private final GameSessionManager gameSessionManager = GameSessionManager.getInstance();


    public void gameStart() {

        while (!gameSessionManager.isLoggedIn()) {
            Profile profile = selectStartMenuOption(true);
            if (profile != null) {
                profile.setCompletedLevels(profileService.getCompletedLevelsByProfile(profile));
                gameSessionManager.setProfile(profile);
            }
        }
        if (gameSessionManager.isLoggedIn()) {
            menuUtil.showUserProfile(gameSessionManager.getProfile());
            manageModeSelected(menuUtil.showModeMenu());

            levelService.playLevel(gameSessionManager.getLevelSelected());
        }
    }

    public Profile retrieveOrCreateProfile(final boolean create) {
        final HashMap<String, String> credentials = menuUtil.showCredentialsMenu();
        if (create) {
            return profileService.createProfile(credentials.get("username"), credentials.get("password"));
        } else {
            return profileService.profileLogin(credentials.get("username"), credentials.get("password"));
        }
    }

    public Profile selectStartMenuOption(final boolean isFirst) {

        int scelta = menuUtil.showStartGameMenu(isFirst);
        switch (scelta) {
            case 1:
                return retrieveOrCreateProfile(false);
            case 2:
                return retrieveOrCreateProfile(true);
            case 3:
                stopGame();
            default:
                System.out.println("\n⚠️ Scelta non valida. Riprova.");
        }

        return null;
    }

    public void stopGame() {
        gameSessionManager.logOut();
        System.out.println("\n👋 Grazie per aver giocato! Alla prossima! 🎮");
        System.exit(0);
    }

    public void manageModeSelected(int mode) {
        switch (mode) {
            case 1:
                manageLevelSelected(menuUtil.showStoryModeMenu(gameSessionManager.getProfile()));
                break;
            case 2:
                stopGame();
            default:
                System.out.println("\n⚠️ Scelta non valida. Riprova.");
        }
    }

    public void manageLevelSelected(int levelType) {
        switch (levelType) {
            case 1:
                managePlayLevel(gameSessionManager.getProfile().getCompletedLevels().size() + 1, false);
                break;
            case 2:
                managePlayLevel(0, true);
                break;
            case 3:
                stopGame();
            default:
                System.out.println("\n⚠️ Scelta non valida. Riprova.");
        }
    }

    public void managePlayLevel(int levelNumber, boolean retryLevel) {
        if (retryLevel) {
            int selectedLevel = menuUtil.showRetryLevelMenu();
            if (selectedLevel <= 0) {
                manageModeSelected(1);
                return;
            }
            gameSessionManager.setLevelSelected(selectedLevel);
        } else {
            gameSessionManager.setLevelSelected(levelNumber);
        }
    }

}
