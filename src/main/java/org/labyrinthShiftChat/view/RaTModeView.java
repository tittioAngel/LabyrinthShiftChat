package org.labyrinthShiftChat.view;

import org.labyrinthShiftChat.model.DifficultyLevel;
import org.labyrinthShiftChat.util.controls.RandomRotationStrategy;
import org.labyrinthShiftChat.util.controls.Direction;

public class RaTModeView extends BaseView{

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    public int readIntInput(String prompt) {
        return super.readInt(prompt);
    }

    public void showInfoRATMode(){
        System.out.println("\n🎮 Benvenuto nella modalità CORSA CONTRO IL TEMPO!");
        System.out.println("\n📜 Cerca di completare più labirinti possibili nel tempo stabilito dalla difficoltà scelta!");
        System.out.println("🧭 Attenzione: i comandi cambiano direzione ad ogni labirinto risolto, dovrai adattarti in fretta!");
        System.out.println("🎯 Ogni labirinto ha anche un numero massimo di mosse: pensa bene prima di agire!");
        System.out.println("🔥 Sfida i tuoi riflessi e la tua mente in una corsa contro il tempo sempre più frenetica!");
    }


    public void showRaTModeMenu() {
        System.out.println("1️⃣  Gioca la modalità Sfida");
        System.out.println("2️⃣  ❌ Esci dal gioco");
        System.out.println("3️⃣  🔙 Torna al menu delle modalità");
    }

    public void getMappedControlsInfo(RandomRotationStrategy controls) {
         System.out.println("\n🔁 Mappatura comandi attuale:\n" +
                "  W ➡️ " + controls.mapInput(Direction.UP) + "\n" +
                "  A ➡️ " + controls.mapInput(Direction.LEFT) + "\n" +
                "  S ➡️ " + controls.mapInput(Direction.DOWN) + "\n" +
                "  D ➡️ " + controls.mapInput(Direction.RIGHT));
    }

    public void showDifficultyGame(){
        System.out.println("\n🎮 Scegli la difficoltà della tua CORSA CONTRO IL TEMPO!");
        int index = 1;
        for (DifficultyLevel difficultyLevel: DifficultyLevel.values()) {
            System.out.println(index + "️⃣  " + DifficultyLevel.formatDifficultyLevel(difficultyLevel));
            index++;
        }
        System.out.println(index + "️⃣  🔙 Torna al menu precedente");
    }

    public void printTimeBar(long remainingMillis, long totalMillis) {
        int barLength = 30;
        double percentage = (double) remainingMillis / totalMillis;
        int filledLength = (int) (barLength * percentage);

        StringBuilder bar = new StringBuilder("⏳ [");
        bar.append("█".repeat(Math.max(0, filledLength)));
        bar.append(" ".repeat(Math.max(0, barLength - filledLength)));
        bar.append("] ");
        bar.append((remainingMillis / 1000)).append("s rimanenti");

        System.out.println(bar);
    }



}
