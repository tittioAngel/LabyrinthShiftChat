package org.labyrinthShiftChat.view;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AccessMenuView extends BaseView {

    @Override
    public void print(String message) {

    }

    @Override
    public void show() {
        System.out.println("1️⃣  Accedi");
        System.out.println("2️⃣  Crea Nuovo Profilo");
        System.out.println("3️⃣  Esci dal gioco");
    }

    public void showStartMenu(int i) {
        if (i == 0) {
            System.out.println("📜 Scegli un'opzione:");
        } else {
            System.out.println("❌ Se hai effettuato la LOGIN: Username e/o password errati. \n❌ Se hai provato a REGISTRATI: L'username esiste già.");
            System.out.println("📜 Scegli un'opzione:");
        }
        show();
    }


    public int readIntInput(String prompt) {
        return super.readInt(prompt);
    }
}
