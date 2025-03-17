package org.labyrinthShiftChat.view;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GamePlayStoryView extends BaseView{

    public void showTotalMiniMaze(char[][] grid) {

        int size = grid.length;
        // Stampa la griglia
        System.out.println("\n🔍 Pre-visualizzazione del labirinto:");
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                System.out.print(grid[x][y] + " ");
            }
            System.out.println();
        }
    }

    public void showLimitedMiniMaze(char[][] grid) {
        int size = grid.length;
        // Stampa la griglia limitata
        System.out.println("\n👀 Vista limitata del labirinto:");
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                System.out.print(grid[x][y] + " ");
            }
            System.out.println();
        }
    }

    public String readString(String prompt ) {return super.readString(prompt);
    }

    @Override
    public void print(String message) {

    }


}
