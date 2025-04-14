package org.labyrinthShiftChat.view;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GamePlayView extends BaseView{

    public void showMiniMaze(char[][] grid, boolean isTotal) {
        int size = grid.length;

        if (isTotal) {
            System.out.println("\n🔍 Pre-visualizzazione del labirinto:");
        } else {
            System.out.println("\n👀 Vista limitata del labirinto:");
        }
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                char symbol = grid[x][y];
                switch (symbol) {
                    case 'S':
                        System.out.print("🏁 "); // Punto di ingresso
                        break;
                    case 'O':
                        System.out.print("🚧 "); // Ostacolo
                        break;
                    case 'N':
                        System.out.print("💀 "); // Nemico
                        break;
                    case 'E':
                        System.out.print("🚪 "); // Uscita
                        break;
                    case 'G':
                        System.out.print("👾 "); // Giocatore
                        break;
                    case '#':
                        System.out.print("🧱 "); // Muro
                        break;
                    case '.':
                        System.out.print("⬜ "); // Corridoio
                        break;
                    default:
                        System.out.print(symbol + " "); // Per altri caratteri, se ce ne sono
                }
            }
            System.out.println();
        }
    }

    public String readString(String prompt ) {return super.readString(prompt);
    }

    @Override
    public void print(String message) {
        System.out.println(message);
    }


}
