package org.labyrinthShiftChat.controller;

import lombok.NoArgsConstructor;
import org.labyrinthShiftChat.model.GameMode;
import org.labyrinthShiftChat.model.Profile;
import org.labyrinthShiftChat.service.GameService;
import org.labyrinthShiftChat.service.LevelService;
import org.labyrinthShiftChat.singleton.GameSessionManager;
import org.labyrinthShiftChat.view.AccessMenuView;
import org.labyrinthShiftChat.view.ModeMenuView;

@NoArgsConstructor
public class GameController {

    private final GameSessionManager gameSessionManager = GameSessionManager.getInstance();
    private final LoginController loginController = new LoginController();
    private final StoryModeController storyModeController = new StoryModeController();

    private final GameService gameService = new GameService();
    private final LevelService levelService = new LevelService();

    private final AccessMenuView accessMenuView = new AccessMenuView();
    private final ModeMenuView modeMenuView = new ModeMenuView();

    public void startGame() throws InterruptedException {
        System.out.println("🎮 Benvenuto in LabyrinthShiftChat! 🎮");
        int i = 0;
        Profile profile = null;

        while (profile == null) {
            accessMenuView.showStartMenu(i);

            int accessType = accessMenuView.readIntInput("👉 Scelta: ");

            switch (accessType) {
                case 1 -> profile = loginController.manageUserLogin();
                case 2 -> profile = loginController.manageUserSignUp();
                case 3 -> stopGame();
                default -> System.out.println("\n⚠️ Scelta non valida. Riprova.");

            }

            i++;
        }

        int levelSelected = 0;
        while (gameSessionManager.isLoggedIn()) {

            modeMenuView.show();

            int modeChoice;
            int gameModeCount = GameMode.values().length;
            do {
                modeChoice = modeMenuView.readIntInput("👉 Scelta: ");

                if (modeChoice < 1 || modeChoice > gameModeCount + 2) {
                    System.out.println("⚠️ Scelta non valida. Riprova.");
                }
            } while (modeChoice < 1 || modeChoice > gameModeCount + 1);

            if (modeChoice == gameModeCount + 1) {
                stopGame();
            } else {
                levelSelected = manageSelectedMode(GameMode.values()[modeChoice - 1]);
                int stars= storyModeController.playLevel(levelSelected);
                storyModeController.manageEndLevel(stars);

            }
        }
    }


    public int manageSelectedMode(GameMode gameMode) {
        switch (gameMode) {
            case STORY_MODE:
                return storyModeController.startStoryMode();
            default:
                System.out.println("\n⚠️ Scelta non valida. Riprova.");
                return 0;
        }
    }

    public void stopGame() {
        gameService.stopGame();
    }



}
