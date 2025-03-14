package org.labirynthShiftChat.controller;

import lombok.NoArgsConstructor;
import org.labirynthShiftChat.model.CompletedLevel;
import org.labirynthShiftChat.singleton.GameSessionManager;
import org.labirynthShiftChat.view.ProfileView;
import org.labirynthShiftChat.view.StoryModeView;

import java.util.List;

@NoArgsConstructor
public class StoryModeController {

    private final GameSessionManager gameSessionManager = GameSessionManager.getInstance();

    private final GameController gameController = new GameController();

    private final StoryModeView storyModeView = new StoryModeView();
    private final ProfileView profileView = new ProfileView();

    public boolean startStoryMode() {

        boolean stayInStoryMode = true;
        while (stayInStoryMode) {
            profileView.show();

            storyModeView.show();

            int input;
            do {
                input = storyModeView.readIntInput("👉 Scelta: ");

                if (input < 1 || input > 4) {
                    System.out.println("⚠️ Scelta non valida. Riprova.");
                }
            } while (input < 1 || input > 4);

            switch (input) {
                case 1 -> {
                    gameSessionManager.setLevelSelected(
                            gameController.obtainLevelToPlay(gameSessionManager.getProfile().getCompletedLevels().size() + 1)
                    );
                    stayInStoryMode = false;
                }
                case 2 -> {
                    showRetryLevelMenu();
                    stayInStoryMode = false;
                }
                case 3 -> gameController.stopGame();
                case 4 -> {
                    return true;
                }
                default -> System.out.println("\n⚠️ Scelta non valida. Riprova.");
            }
        }
        return true;
    }

    public void showRetryLevelMenu() {
        List<CompletedLevel> completedLevels = gameSessionManager.getProfile().getCompletedLevels();

        if (completedLevels.isEmpty()) {
            System.out.println("\n⚠️ Non hai ancora completato alcun livello da riprovare.");
            return;
        }

        boolean stayInRetryMenu = true;

        while (stayInRetryMenu) {
            storyModeView.showRetryMenu();

            int levelSelected;
            do {
                levelSelected = storyModeView.readIntInput("👉 Inserisci il numero del livello: : ");

                if (levelSelected < 1 || levelSelected > completedLevels.size() + 2) {
                    System.out.println("❌ Scelta non valida. Riprova.");
                }
            } while (levelSelected < 1 || levelSelected > completedLevels.size() + 2);

            if (levelSelected == completedLevels.size() + 2) {
                gameController.stopGame();
            } else if (levelSelected == completedLevels.size() + 1) {
                stayInRetryMenu = false;
            } else {
                gameSessionManager.setLevelSelected(gameController.obtainLevelToPlay(levelSelected));
                stayInRetryMenu = false;
            }
        }
    }

}
