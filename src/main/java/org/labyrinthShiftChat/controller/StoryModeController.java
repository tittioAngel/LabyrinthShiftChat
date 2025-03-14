package org.labyrinthShiftChat.controller;

import lombok.NoArgsConstructor;
import org.labyrinthShiftChat.model.CompletedLevel;
import org.labyrinthShiftChat.service.GameService;
import org.labyrinthShiftChat.service.LevelService;
import org.labyrinthShiftChat.singleton.GameSessionManager;
import org.labyrinthShiftChat.view.ProfileView;
import org.labyrinthShiftChat.view.StoryModeView;

import java.util.List;

@NoArgsConstructor
public class StoryModeController {

    private final GameSessionManager gameSessionManager = GameSessionManager.getInstance();

    private final LevelService levelService = new LevelService();
    private final GameService gameService = new GameService();

    private final StoryModeView storyModeView = new StoryModeView();
    private final ProfileView profileView = new ProfileView();

    public int startStoryMode() {

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
                    int levelIdentifier = gameSessionManager.getProfile().getCompletedLevels().size() + 1;
                    gameSessionManager.setLevelSelected(levelService.findLevelByNumber(levelIdentifier));
                    return levelIdentifier;
                }
                case 2 -> {
                    if (showRetryLevelMenu() != -1)
                        return showRetryLevelMenu();
                }
                case 3 -> gameService.stopGame();
                case 4 -> {
                    stayInStoryMode = false;
                }
                default -> System.out.println("\n⚠️ Scelta non valida. Riprova.");
            }
        }

        return 0;
    }

    public int showRetryLevelMenu() {
        List<CompletedLevel> completedLevels = gameSessionManager.getProfile().getCompletedLevels();

        if (completedLevels.isEmpty()) {
            System.out.println("\n⚠️ Non hai ancora completato alcun livello da riprovare.");
            return -1;
        }

        boolean stayInRetryMenu = true;

        while (stayInRetryMenu) {
            storyModeView.showRetryMenu();

            int levelSelected;
            do {
                levelSelected = storyModeView.readIntInput("👉 Inserisci il numero del livello: ");

                if (levelSelected < 1 || levelSelected > completedLevels.size() + 2) {
                    System.out.println("❌ Scelta non valida. Riprova.");
                }
            } while (levelSelected < 1 || levelSelected > completedLevels.size() + 2);

            if (levelSelected == completedLevels.size() + 2) {
                gameService.stopGame();
            } else if (levelSelected == completedLevels.size() + 1) {
                stayInRetryMenu = false;
            } else {
                gameSessionManager.setLevelSelected(levelService.findLevelByNumber(levelSelected));
                return levelSelected;
            }
        }
        return 0;
    }

}
