package org.example.util;

import lombok.NoArgsConstructor;
import org.example.model.CompletedLevel;
import org.example.model.Level;
import org.example.model.Profile;
import org.example.service.ProfileService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@NoArgsConstructor
public class MenuUtil {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ProfileService profileService = new ProfileService();

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
        System.out.println("🎮 PROFILO UTENTE: " + profile.getUsername());
        System.out.println("🏆 Livelli completati:");

        List<CompletedLevel> completedLevels = profileService.getCompletedLevelsByProfile(profile);
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
            System.out.println("\n   ❌ Nessun livello completato.");
        }
    }


    public int showLevelsMenu(Profile profile) {
        System.out.println("🎮 Scegli che livello giocare: ");
        System.out.println("1️⃣  Gioca il prossimo livello (Livello " + profile.getCompletedLevels().size() + 1 + ")");
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
}
