package org.example.service;

import lombok.NoArgsConstructor;
import org.example.model.Profile;
import org.example.singleton.SessionFactory;
import org.example.util.MenuUtil;

import java.util.HashMap;

@NoArgsConstructor
public class GameService {

    private final MenuUtil menuUtil = new MenuUtil();
    private final ProfileService profileService = new ProfileService();
    private final LevelService levelService = new LevelService();

    private final SessionFactory sessionFactory = SessionFactory.getInstance();


    public void gameStart() {

        while (!sessionFactory.isLoggedIn()) {
            Profile profile = selectStartMenuOption(true);
            if (profile != null) {
                sessionFactory.setProfile(profile);
            }
        }
        if (sessionFactory.isLoggedIn()) {
            menuUtil.showUserProfile(sessionFactory.getProfile());
            manageModeSelected(menuUtil.showModeMenu());
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
        sessionFactory.logOut();
        System.out.println("\n👋 Grazie per aver giocato! Alla prossima! 🎮");
        System.exit(0);
    }

    public void manageModeSelected(int mode) {
        switch (mode) {
            case 1:
                manageLevelSelected(menuUtil.showLevelsMenu(sessionFactory.getProfile()));
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
                managePlayLevel(sessionFactory.getProfile().getCompletedLevels().size() + 1 );
        }
    }

    public void managePlayLevel(int levelNumber) {
        levelService.playLevel(levelNumber);
    }

}
