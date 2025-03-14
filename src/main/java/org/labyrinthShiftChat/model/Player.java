package org.labyrinthShiftChat.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;

@Getter
@Setter
@NoArgsConstructor
public class Player {
    private int x;
    private int y;
    private int speed = 1;

    private static final int MAX_HISTORY = 5;  // Numero massimo di posizioni memorizzate
    private LinkedList<int[]> positionHistory = new LinkedList<>();

    public Player(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        addPositionToHistory(startX, startY);
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        addPositionToHistory(x, y);
    }

    private void addPositionToHistory(int x, int y) {
        if (positionHistory.size() >= MAX_HISTORY) {
            positionHistory.removeLast(); // Rimuove la posizione più vecchia
        }
        positionHistory.addFirst(new int[]{x, y}); // Aggiunge la nuova posizione in testa
    }

    public int[] getPreviousPosition(int stepsBack) {
        if (stepsBack < positionHistory.size()) {
            return positionHistory.get(stepsBack);
        }
        return new int[]{x, y}; // Se non ci sono abbastanza posizioni, ritorna la posizione attuale
    }

}
