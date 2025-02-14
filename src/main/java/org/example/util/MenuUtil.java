package org.example.util;

import lombok.NoArgsConstructor;
import org.example.model.CompletedLevel;
import org.example.model.Profile;
import org.example.service.ProfileService;
import org.example.singleton.GameSessionManager;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

@NoArgsConstructor
public class MenuUtil {

    private static final Scanner scanner = new Scanner(System.in);
    private final GameSessionManager gameSessionManager = GameSessionManager.getInstance();

    public int showStartGameMenu(final boolean isFirst) {
        if (isFirst) {
            System.out.println("📜 Scegli un'opzione:");
        } else {
            System.out.println("❌ Credenziali errate. Scegli un'opzione:");
        }
        System.out.println("1️⃣  Accedi");
        System.out.println("2️⃣  Crea Nuovo Profilo");
        System.out.println("3️⃣  Esci dal gioco");
        System.out.print("👉 Scelta: ");

        int scelta = scanner.nextInt();
        scanner.nextLine();

        return scelta;
    }

    public HashMap<String, String> showCredentialsMenu() {
        System.out.print("Inserisci username: ");
        String username = scanner.nextLine();
        System.out.print("Inserisci password: ");
        String password = scanner.nextLine();

        HashMap<String, String> credentials = new HashMap<>();
        credentials.put("username", username);
        credentials.put("password", password);

        return credentials;
    }

    public void showUserProfile(Profile profile) {
        System.out.println("------------------------------------------------------");
        System.out.println("🎮 PROFILO UTENTE: " + profile.getUsername());
        System.out.println("🏆 Livelli completati:");

        List<CompletedLevel> completedLevels = profile.getCompletedLevels();
        if (completedLevels != null && !completedLevels.isEmpty()) {
            System.out.println("\n🎮 Livelli Completati:");
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
            System.out.println("------------------------------------------------------  \n");
        }
    }


    public int showStoryModeMenu(Profile profile) {
        System.out.println("\n🎮 Bevenuto nella Modalità Storia!\n Scegli il livello da giocare: ");
        System.out.println("1️⃣  Gioca il prossimo livello [Livello " + profile.getCompletedLevels().size() + 1 + "]");
        System.out.println("2️⃣  Riprova uno dei livelli precedenti");
        System.out.println("3️⃣  Esci dal gioco");
        System.out.print("👉 Scelta: ");

        int scelta = scanner.nextInt();
        scanner.nextLine();

        return scelta;
    }

    public int showModeMenu() {
        System.out.println("🎮 Scegli la modalità da giocare: ");
        System.out.println("1️⃣  Modalità Storia");
        System.out.println("2️⃣  Esci dal gioco");
        System.out.print("👉 Scelta: ");

        int scelta = scanner.nextInt();
        scanner.nextLine();

        return scelta;
    }

    public int showRetryLevelMenu() {
        List<CompletedLevel> completedLevels = gameSessionManager.getProfile().getCompletedLevels();

        if (completedLevels.isEmpty()) {
            System.out.println("\n⚠️ Non hai ancora completato alcun livello da riprovare.");
            return -1;
        }

        System.out.println("\n🔁 Scegli un livello da riprovare:");
        for (int i = 0; i < completedLevels.size(); i++) {
            System.out.println((i + 1) + ". Livello: " + completedLevels.get(i).getLevel().getName());
        }
        System.out.println((completedLevels.size() + 1) + ". Torna al menu principale");

        int scelta;
        do {
            System.out.print("👉 Inserisci il numero del livello: ");
            scelta = scanner.nextInt();
            scanner.nextLine();

            if (scelta < 1 || scelta > completedLevels.size() + 1) {
                System.out.println("❌ Scelta non valida. Riprova.");
            }
        } while (scelta < 1 || scelta > completedLevels.size() + 1);

        if (scelta == completedLevels.size() + 1) {
            return 0;
        }

        return scelta;
    }
}
