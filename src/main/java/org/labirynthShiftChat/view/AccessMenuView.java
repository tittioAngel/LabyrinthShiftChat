package org.labirynthShiftChat.view;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AccessMenuView extends BaseView {

    @Override
    public void show() {
        System.out.println("1️⃣  Accedi");
        System.out.println("2️⃣  Crea Nuovo Profilo");
        System.out.println("3️⃣  Esci dal gioco");
    }

    public int readIntInput(String prompt) {
        return super.readInt(prompt);
    }
}
