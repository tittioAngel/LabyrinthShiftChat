package org.labirynthShiftChat.service;

import org.labirynthShiftChat.model.*;
import org.labirynthShiftChat.model.tiles.Corridor;
import org.labirynthShiftChat.model.tiles.ExitTile;
import org.labirynthShiftChat.model.tiles.StartTile;
import org.labirynthShiftChat.model.tiles.Wall;

import java.util.Random;
import java.util.Stack;

public class MazeGenerator {
    private final Random random = new Random();

    public Maze generateMaze(DifficultyLevel difficulty) {
        int size = difficulty.getMazeSize();
        Maze maze = new Maze(difficulty);

        boolean[][] visited = new boolean[size][size];
        char[][] grid = new char[size][size];

        // Creiamo un labirinto vuoto con tutti muri
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                grid[x][y] = '#'; // Muro
            }
        }

        // Posizione iniziale e finale
        int startX = 0, startY = 0;
        int exitX = size - 1, exitY = size - 1;

        // Stack per DFS
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{startX, startY});
        grid[startX][startY] = '.'; // Corridoio
        visited[startX][startY] = true;

        // Algoritmo DFS per generare il percorso
        while (!stack.isEmpty()) {
            int[] pos = stack.pop();
            int x = pos[0], y = pos[1];

            int[][] directions = {{0, 2}, {2, 0}, {0, -2}, {-2, 0}};
            shuffleArray(directions);

            for (int[] dir : directions) {
                int nx = x + dir[0];
                int ny = y + dir[1];

                if (nx >= 0 && ny >= 0 && nx < size && ny < size && !visited[nx][ny]) {
                    visited[nx][ny] = true;
                    grid[nx][ny] = '.';
                    grid[x + dir[0] / 2][y + dir[1] / 2] = '.';
                    stack.push(new int[]{nx, ny});
                }
            }
        }

        // Assicuriamo che ci sia un'uscita
        grid[exitX][exitY] = 'E';

        // Convertiamo in entità Hibernate
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Tile tile;
                if (x == startX && y == startY) {
                    tile = new StartTile(x, y, maze);
                } else if (x == exitX && y == exitY) {
                    tile = new ExitTile(x, y, maze);
                } else if (grid[x][y] == '.') {
                    tile = new Corridor(x, y, maze);
                } else {
                    tile = new Wall(x, y, maze);
                }
                maze.addTile(tile);
            }
        }

        return maze;
    }

    private void shuffleArray(int[][] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int[] temp = array[i];
            array[i] = array[index];
            array[index] = temp;
        }
    }


}
