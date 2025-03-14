package org.labirynthShiftChat.view;

import lombok.NoArgsConstructor;
import org.labirynthShiftChat.model.CompletedLevel;
import org.labirynthShiftChat.singleton.GameSessionManager;

import java.util.List;

@NoArgsConstructor
public class StoryModeView extends BaseView {

    private final GameSessionManager gameSessionManager = GameSessionManager.getInstance();

    @Override
    public void show() {
        System.out.println("\n🎮 Benvenuto nella Modalità Storia! \n📜 Scegli il livello da giocare:");
        System.out.println("1️⃣  Gioca il prossimo livello [Livello " + (!gameSessionManager.getProfile().getCompletedLevels().isEmpty() ? (gameSessionManager.getProfile().getCompletedLevels().size() + 1) : 1) + "]");
        System.out.println("2️⃣  Riprova uno dei livelli precedenti");
        System.out.println("3️⃣  Esci dal gioco");
        System.out.println("4️⃣  Torna al menu di scelta della modalità di gioco");
    }

    public int readIntInput(String prompt) {
        return super.readInt(prompt);
    }

    public void showRetryMenu() {
        List<CompletedLevel> completedLevels = gameSessionManager.getProfile().getCompletedLevels();
        System.out.println("\n🔁 Scegli un livello da riprovare:");
        for (int i = 0; i < completedLevels.size(); i++) {
            System.out.println((i + 1) + "️⃣  Livello: " + completedLevels.get(i).getLevel().getName());
        }
        System.out.println((completedLevels.size() + 1) + "️⃣  Torna al menu precedente");
        System.out.println((completedLevels.size() + 2) + "️⃣  Esci dal gioco");
    }
}
