package org.labirynthShiftChat.view;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LoginView extends BaseView {

    @Override
    public void show() {
        System.out.println("📜 Inserisci le credenziali per accedere/registrarti 📜");
    }

    public String readStringInput(String prompt) {
        return super.readString(prompt);
    }
}
