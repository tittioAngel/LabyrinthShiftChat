package org.labyrinthShiftChat.controller;

import lombok.NoArgsConstructor;
import org.labyrinthShiftChat.model.GameMode;
import org.labyrinthShiftChat.model.Profile;
import org.labyrinthShiftChat.service.GameService;
import org.labyrinthShiftChat.view.AccessMenuView;
import org.labyrinthShiftChat.view.ModeMenuView;

@NoArgsConstructor
public class GameController {

    private final LoginController loginController = new LoginController();
    private final StoryModeController storyModeController = new StoryModeController();

    private final GameService gameService = new GameService();

    private final AccessMenuView accessMenuView = new AccessMenuView();
    private final ModeMenuView modeMenuView = new ModeMenuView();

    public void startGame() throws InterruptedException {
        accessMenuView.print("🎮 Benvenuto in LabyrinthShiftChat! 🎮");
        int i = 0;
        Profile profile = null;

        while (profile == null) {
            accessMenuView.showStartMenu(i);

            int accessType = accessMenuView.readIntInput("👉 Scelta: ");

            switch (accessType) {
                case 1 -> profile = loginController.manageUserLogin();
                case 2 -> profile = loginController.manageUserSignUp();
                case 3 -> stopGame();
                default -> accessMenuView.print("\n⚠️ Scelta non valida. Riprova.");
            }

            i++;
        }

        while (true) {
            modeMenuView.showModeMenu();

            int modeChoice;
            int gameModeCount = GameMode.values().length;
            do {
                modeChoice = modeMenuView.readIntInput("👉 Scelta: ");

                if (modeChoice < 1 || modeChoice > gameModeCount + 2) {
                    modeMenuView.print("⚠️ Scelta non valida. Riprova.");
                }
            } while (modeChoice < 1 || modeChoice > gameModeCount + 1);

            if (modeChoice == gameModeCount + 1) {
                stopGame();
            } else {
                manageSelectedMode(GameMode.values()[modeChoice - 1]);
            }
        }

    }


    public void manageSelectedMode(GameMode gameMode) throws InterruptedException {
        switch (gameMode) {
            case STORY_MODE -> storyModeController.startStoryMode();
            default -> modeMenuView.print("\n⚠️ Scelta non valida. Riprova.");
        }
    }

    public void stopGame() {
        gameService.stopGame();
    }



}
