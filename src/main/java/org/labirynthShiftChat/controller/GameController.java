package org.labirynthShiftChat.controller;

import lombok.NoArgsConstructor;
import org.labirynthShiftChat.model.GameMode;
import org.labirynthShiftChat.model.Level;
import org.labirynthShiftChat.service.GameService;
import org.labirynthShiftChat.service.LevelService;
import org.labirynthShiftChat.singleton.GameSessionManager;
import org.labirynthShiftChat.view.AccessMenuView;
import org.labirynthShiftChat.view.ModeMenuView;

@NoArgsConstructor
public class GameController {

    private final GameSessionManager gameSessionManager = GameSessionManager.getInstance();
    private final LoginController loginController = new LoginController();
    private final StoryModeController storyModeController = new StoryModeController();

    private final GameService gameService = new GameService();
    private final LevelService levelService = new LevelService();

    private final AccessMenuView accessMenuView = new AccessMenuView();
    private final ModeMenuView modeMenuView = new ModeMenuView();

    public void startGame() {
        System.out.println("🎮 Benvenuto in LabyrinthShiftChat! 🎮");
        int i = 0;
        while (!gameSessionManager.isLoggedIn()) {
            if (i == 0) {
                System.out.println("📜 Scegli un'opzione:");
            } else {
                System.out.println("❌ Se hai effettuato la LOGIN: Username e/o password errati. \n❌Se hai provato a REGISTRATI: L'username esiste già.");
                System.out.println("📜 Scegli un'opzione:");
            }

            accessMenuView.show();
            int accessType = accessMenuView.readIntInput("👉 Scelta: ");

            manageAccessType(accessType);
        }

        boolean keepPlaying = true;
        while (keepPlaying && !gameSessionManager.isLevelSelected()) {
            manageModeChoice();
        }
    }

    public void manageAccessType(int accessType) {
        switch (accessType) {
            case 1 -> loginController.manageUserLogin();
            case 2 -> loginController.manageUserSignUp();
            case 3 -> stopGame();
            default -> System.out.println("\n⚠️ Scelta non valida. Riprova.");

        }
    }

    public boolean manageModeChoice() {
        modeMenuView.show();

        int modeChoice;
        int gameModeCount = GameMode.values().length;
        do {
            modeChoice = modeMenuView.readIntInput("👉 Scelta: ");

            if (modeChoice < 1 || modeChoice > gameModeCount) {
                System.out.println("⚠️ Scelta non valida. Riprova.");
            }
        } while (modeChoice < 1 || modeChoice > gameModeCount);

        if (modeChoice == gameModeCount) {
            stopGame();
        } else {
            return manageSelectedMode(GameMode.values()[modeChoice - 1]);
        }
    }

    public boolean manageSelectedMode(GameMode gameMode) {
        switch (gameMode) {
            case STORY_MODE:
                return storyModeController.startStoryMode();
            default:
                System.out.println("\n⚠️ Scelta non valida. Riprova.");
                return true;
        }
    }

    public Level obtainLevelToPlay(int levelIdentifier) {
        return levelService.findLevelByNumber(levelIdentifier);
    }

    public void stopGame() {
        gameService.stopGame();
    }


}
