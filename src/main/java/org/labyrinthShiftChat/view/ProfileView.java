package org.labyrinthShiftChat.view;

import lombok.NoArgsConstructor;
import org.labyrinthShiftChat.model.CompletedLevel;
import org.labyrinthShiftChat.model.DifficultyLevel;
import org.labyrinthShiftChat.model.Profile;

import java.util.List;

@NoArgsConstructor
public class ProfileView extends BaseView {

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    public void showProfileInfo(Profile profile) {
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


    public void showRecordProfile(Profile profile){
        System.out.println("\n------------------------------------------------------");
        System.out.println("🎮 PROFILO UTENTE: " + profile.getUsername());
        System.out.println("------------------------------------------------------");

        System.out.println("+----------------+----------------------+");
        System.out.println("|  Difficoltà    |     MiniLabirinti    |");
        System.out.println("+----------------+----------------------+");

        System.out.printf ("| %-14s | %-20s |\n", DifficultyLevel.EASY.getDifficultyName(), formatRecord(profile.getRecordRatEasy()));
        System.out.printf ("| %-14s | %-20s |\n", DifficultyLevel.MEDIUM.getDifficultyName(), formatRecord(profile.getRecordRatMedium()));
        System.out.printf ("| %-14s | %-20s |\n", DifficultyLevel.HARD.getDifficultyName(), formatRecord(profile.getRecordRatHard()));
        System.out.printf ("| %-14s | %-20s |\n", DifficultyLevel.EXTREME.getDifficultyName(), formatRecord(profile.getRecordRatExtreme()));
        System.out.println("+----------------+----------------------+");
    }

    private String formatRecord(Integer record){
        return (record != null) ? record.toString() : "0";
    }
}
