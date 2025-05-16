package org.labyrinthShiftChat.view;

import lombok.NoArgsConstructor;
import org.labyrinthShiftChat.model.tiles.MazeSymbolRegistry;

@NoArgsConstructor
public class GamePlayView extends BaseView {

    public void showMiniMaze(char[][] grid, boolean isTotal) {
        int size = grid.length;

        if (isTotal) {
            System.out.println("\n🔍 Visualizzazione completa del labirinto:");
        } else {
            System.out.println("\n👀 Vista limitata del labirinto:");
        }

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                char symbol = grid[x][y];
                String emoji = MazeSymbolRegistry.getEmojiForSymbol(symbol);
                System.out.print(emoji);
            }
            System.out.println();
        }
    }

    public String readString(String prompt) {
        return super.readString(prompt);
    }

    @Override
    public void print(String message) {
        System.out.println(message);
    }


}
