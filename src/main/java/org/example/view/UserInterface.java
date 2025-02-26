package org.example.view;

import lombok.Getter;
import lombok.Setter;
import org.example.controller.GameController;
import org.example.controller.GameSessionController;
import org.example.dao.LevelDAO;
import org.example.model.CompletedLevel;
import org.example.model.GameMode;
import org.example.model.Level;
import org.example.model.Profile;
import org.example.singleton.GameSessionManager;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

@Getter
@Setter
public class UserInterface {
    private final GameController gameController;
    private final GameSessionController gameSessionController;
    private final GameSessionManager gameSessionManager = GameSessionManager.getInstance();
    private final Scanner scanner;
    private LevelDAO levelDAO = new LevelDAO();

    public UserInterface(GameController gameController, GameSessionController gameSessionController) {
        this.gameController = gameController;
        this.gameSessionController = gameSessionController;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("🎮 Benvenuto in LabyrinthShiftChat! 🎮");

        int i = 0;
        while (!gameSessionManager.isLoggedIn()) {
            if (i == 0) {
                System.out.println("📜 Scegli un'opzione:");
            } else {
                System.out.println("❌ Qualcosa è andato storto. Riprova. \n📜 Scegli un'opzione:");
            }
            System.out.println("1️⃣  Accedi");
            System.out.println("2️⃣  Crea Nuovo Profilo");
            System.out.println("3️⃣  Esci dal gioco");
            System.out.print("👉 Scelta: ");

            int input = scanner.nextInt();
            scanner.nextLine();

            switch (input) {
                case 1 -> gameController.manageUserLogin(retrieveCredentials());
                case 2 -> gameController.manageUserSignUp(retrieveCredentials());
                case 3 -> gameController.stopGame();
                default -> System.out.println("\n⚠️ Scelta non valida. Riprova.");
            }
            i++;
        }

        boolean keepPlaying = true;
        while (keepPlaying && !gameSessionManager.isLevelSelected()) {
            System.out.println("\n🎮 Scegli la modalità da giocare: ");
            int index = 1;
            for (GameMode mode : GameMode.values()) {
                System.out.println(index + "️⃣  " + GameMode.formatGameMode(mode));
                index++;
            }
            System.out.println(index + "️⃣  Esci dal gioco");

            int input;
            do {
                System.out.print("👉 Scelta: ");
                input = scanner.nextInt();
                scanner.nextLine();

                if (input < 1 || input > index) {
                    System.out.println("⚠️ Scelta non valida. Riprova.");
                }
            } while (input < 1 || input > index);

            if (input == index) {
                gameController.stopGame();
            } else {
                keepPlaying = manageSelectedMode(GameMode.values()[input - 1]);
            }
        }

        gameSessionController.startNewGameSession();

    }

    public HashMap<String, String> retrieveCredentials() {
        System.out.print("Inserisci username: ");
        String username = scanner.nextLine();
        System.out.print("Inserisci password: ");
        String password = scanner.nextLine();

        HashMap<String, String> credentials = new HashMap<>();
        credentials.put("username", username);
        credentials.put("password", password);

        return credentials;
    }

    public boolean manageSelectedMode(GameMode gameMode) {
        switch (gameMode) {
            case STORY_MODE:
                return manageStoryMode();
            default:
                System.out.println("\n⚠️ Scelta non valida. Riprova.");
                return true;
        }
    }


    public void showUserProfile(Profile profile) {
        System.out.println("\n------------------------------------------------------");
        System.out.println("🎮 PROFILO UTENTE: " + profile.getUsername());
        System.out.println("------------------------------------------------------");
        System.out.println("\n 🏆 Livelli completati:");

        List<CompletedLevel> completedLevels = profile.getCompletedLevels();
        if (completedLevels != null && !completedLevels.isEmpty()) {
            System.out.println("------------------------------------------------------");
            System.out.printf("| %-20s | %-10s | %-8s |\n", "Livello", "Difficoltà", "Punteggio");
            System.out.println("------------------------------------------------------");

            for (CompletedLevel completedLevel : completedLevels) {
                System.out.printf("| %-20s | %-10s | %-8d |\n",
                        completedLevel.getLevel().getName(),
                        completedLevel.getLevel().getDifficultyLevel().toString(),
                        completedLevel.getScore()
                );
            }

            System.out.println("------------------------------------------------------");
        } else {
            System.out.println("    ❌ Nessun livello completato.");
            System.out.println("------------------------------------------------------");
        }
    }

    public boolean manageStoryMode() {
        boolean stayInStoryMode = true;

        while (stayInStoryMode) {
            showUserProfile(gameSessionManager.getProfile());
            System.out.println("\n🎮 Benvenuto nella Modalità Storia! \n📜 Scegli il livello da giocare:");
            System.out.println("1️⃣  Gioca il prossimo livello [Livello " + (gameSessionManager.getProfile().getCompletedLevels().size() + 1) + "]");
            System.out.println("2️⃣  Riprova uno dei livelli precedenti");
            System.out.println("3️⃣  Esci dal gioco");
            System.out.println("4️⃣  Torna al menu di scelta della modalità di gioco");

            int input;
            do {
                System.out.print("👉 Scelta: ");
                input = scanner.nextInt();
                scanner.nextLine();

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
                case 2 -> showRetryLevelMenu();
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
            System.out.println("\n🔁 Scegli un livello da riprovare:");
            for (int i = 0; i < completedLevels.size(); i++) {
                System.out.println((i + 1) + "️⃣  Livello: " + completedLevels.get(i).getLevel().getName());
            }
            System.out.println((completedLevels.size() + 1) + "️⃣  Torna al menu precedente");
            System.out.println((completedLevels.size() + 2) + "️⃣  Esci dal gioco");

            int scelta;
            do {
                System.out.print("👉 Inserisci il numero del livello: ");
                scelta = scanner.nextInt();
                scanner.nextLine();

                if (scelta < 1 || scelta > completedLevels.size() + 2) {
                    System.out.println("❌ Scelta non valida. Riprova.");
                }
            } while (scelta < 1 || scelta > completedLevels.size() + 2);

            if (scelta == completedLevels.size() + 2) {
                gameController.stopGame();
            } else if (scelta == completedLevels.size() + 1) {
                stayInRetryMenu = false;
            } else {
                gameSessionManager.setLevelSelected(gameController.obtainLevelToPlay(scelta));
                gameSessionController.startNewGameSession();
                stayInRetryMenu = false;
            }
        }
    }
}