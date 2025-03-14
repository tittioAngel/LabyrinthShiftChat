package org.labirynthShiftChat.view;

import lombok.NoArgsConstructor;
import org.labirynthShiftChat.model.GameMode;

@NoArgsConstructor
public class ModeMenuView extends BaseView {

    @Override
    public void show() {
        System.out.println("\n🎮 Scegli la modalità da giocare: ");
        int index = 1;
        for (GameMode mode : GameMode.values()) {
            System.out.println(index + "️⃣  " + GameMode.formatGameMode(mode));
            index++;
        }
        System.out.println(index + "️⃣  Esci dal gioco");
    }

    public int readIntInput(String prompt) {
        return super.readInt(prompt);
    }
}
