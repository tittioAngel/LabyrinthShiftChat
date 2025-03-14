package org.labirynthShiftChat.view;

import java.util.Scanner;

public abstract class BaseView {
    protected static final Scanner scanner = new Scanner(System.in);

    protected String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    protected int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Errore: inserisci un numero valido.");
            }
        }
    }

    protected double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Errore: inserisci un numero decimale valido.");
            }
        }
    }

    public abstract void show(); // Ogni view dovrà implementare il metodo show()
}
