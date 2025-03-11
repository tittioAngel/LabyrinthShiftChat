package org.example.view;

import lombok.Getter;
import lombok.Setter;
import org.example.controller.GameController;
import org.example.controller.GameSessionController;
import org.example.model.*;
import org.example.model.tiles.ExitTile;
import org.example.service.GameSessionService;
import org.example.singleton.GameSessionManager;

import java.util.*;

@Getter
@Setter
public class UserInterface {
    private final GameController gameController;
    private final GameSessionController gameSessionController;
    private final GameSessionManager gameSessionManager = GameSessionManager.getInstance();
    private final Scanner scanner;
    private static final int TOTAL_MINIMAZES = 3;

    public UserInterface(GameController gameController, GameSessionController gameSessionController) {
        this.gameController = gameController;
        this.gameSessionController = gameSessionController;
        this.scanner = new Scanner(System.in);
    }

    public void start() throws InterruptedException {
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

        if (gameSessionManager.isLevelSelected()) {
            Level level = gameSessionManager.getLevelSelected();
            int miniMazeCompleted = 0;
            int totalStars = 0;

            System.out.println("\n🎮 Livello selezionato: " + level.getName());

            while (miniMazeCompleted < TOTAL_MINIMAZES) {
                gameSessionController.createGameSession();

                System.out.println("\n🌀 Inizio Minimaze " + (miniMazeCompleted + 1) + " di " + TOTAL_MINIMAZES);

                gameSessionController.showMiniMazePreview();

                int stars = managePlayerMovement();
                totalStars += stars;

                System.out.println("\n🏆 Hai completato il MiniMaze " + (miniMazeCompleted + 1) + " con punteggio : " + stars + "/3");

                Thread.sleep(3000);

                miniMazeCompleted++;
            }

            manageEndLevel(totalStars);
        }
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
                stayInRetryMenu = false;
            }
        }
    }

    public int managePlayerMovement() {
        System.out.println("✅ Il gioco inizia ora con la visione limitata!");

        long startTime = System.currentTimeMillis(); // Avvio del timer locale
        long timeLimit = 60 * 1000; // 60 secondi in millisecondi

        while (true) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            long remainingTime = (timeLimit - elapsedTime) / 1000;

            // Se il tempo è scaduto, rigeneriamo il minimaze e resettare il timer
            if (remainingTime <= 0) {
                System.out.println("⏳ Tempo scaduto! Rigenerazione del minimaze...");
                gameSessionController.regenerateMiniMaze();
                // Reset del timer per il nuovo tentativo sullo stesso minimaze
                startTime = System.currentTimeMillis();
                continue; // Riprende il ciclo senza uscire
            }

            gameSessionController.showLimitedMiniMazeView();
            System.out.println("\n⏳ Tempo rimasto: " + remainingTime + " secondi.");
            System.out.print("➡️ Inserisci la direzione (WASD per muoverti, Q per uscire): ");

            String direction = scanner.nextLine().toUpperCase();

            int stars = gameSessionController.manageDirectionSelected(direction, startTime);

            if (stars != 0) {
                return stars;
            }
        }
    }

    public void manageEndLevel(int totalStars) {

        int averageStars = totalStars / TOTAL_MINIMAZES;
        gameSessionController.saveCompletedLevel(averageStars);

        System.out.println("\n🏆 **Complimenti! Hai completato tutti i minimaze del livello.** 🏆");
        System.out.println("⭐ Punteggio finale medio: " + averageStars + " stelle.");

        gameSessionManager.resetSession();
    }
}
