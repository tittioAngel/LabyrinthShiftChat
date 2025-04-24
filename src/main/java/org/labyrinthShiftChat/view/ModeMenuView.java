package org.labyrinthShiftChat.view;

import lombok.NoArgsConstructor;
import org.labyrinthShiftChat.model.GameMode;

@NoArgsConstructor
public class ModeMenuView extends BaseView {

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    public void showModeMenu() {
        System.out.println("\n🎮 Scegli la modalità da giocare: ");
        int index = 1;
        for (GameMode mode : GameMode.values()) {
            System.out.println(index + "️⃣  " + GameMode.formatGameMode(mode));
            index++;
        }
        System.out.println(index + "️⃣  ❌ Esci dal gioco");
    }

    public int readIntInput(String prompt) {
        return super.readInt(prompt);
    }
}
