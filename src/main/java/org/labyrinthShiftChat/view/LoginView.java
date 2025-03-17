package org.labyrinthShiftChat.view;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LoginView extends BaseView {

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    public String readStringInput(String prompt) {
        return super.readString(prompt);
    }
}
