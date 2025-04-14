package org.labyrinthShiftChat.view;

import org.labyrinthShiftChat.model.DifficultyLevel;
import org.labyrinthShiftChat.model.GameMode;

public class RaTModeView extends BaseView{

    @Override
    public void print(String message) {System.out.println(message);}


    public int readIntInput(String prompt) {
        return super.readInt(prompt);
    }


    public void showInfoRATMode(){
        System.out.println("\n🎮 Benvenuto nella modalità corsa contro il tempo! \n📜");
        System.out.println("Cerca di completare più labirinti possibili in 90 secondi");

    }


    public void showRaTModeMenu() {

        int index=1;
        System.out.println((index)+"  Gioca la modalità sfida  " );
        System.out.println((index+1)+" Esci dal gioco");
        System.out.println((index+2)+" Torna al menu di scelta delle modalità di gioco");
    }


    public void showDifficultyGame(){
        System.out.println("\n🎮 Scegli la difficoltà della tua corsa \n📜");
        int index = 1;
        for (GameMode mode : GameMode.values()) {
            System.out.println(index + "️⃣  " + GameMode.formatGameMode(mode));
            index++;
        }
        System.out.println(index + "️⃣  Torna al menu' precedente");
    }


}
