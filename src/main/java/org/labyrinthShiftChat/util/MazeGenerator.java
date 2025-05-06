package org.labyrinthShiftChat.util;

import org.labyrinthShiftChat.dao.MazeComponentDAO;
import org.labyrinthShiftChat.dao.MazeDAO;
import org.labyrinthShiftChat.dao.TileDAO;
import org.labyrinthShiftChat.model.*;
import org.labyrinthShiftChat.model.tiles.MazeComponent;
import org.labyrinthShiftChat.model.tiles.common.Corridor;
import org.labyrinthShiftChat.model.tiles.common.ExitTile;
import org.labyrinthShiftChat.model.tiles.common.StartTile;
import org.labyrinthShiftChat.model.tiles.common.Wall;

import java.util.Random;
import java.util.Stack;

public class MazeGenerator {

    private final Random random = new Random();
    private final MazeDAO mazeDAO = new MazeDAO();
    private final TileDAO tileDAO = new TileDAO();
    private final MazeComponentDAO mazeComponentDAO = new MazeComponentDAO();

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

        // Aggiungiamo dei loop (rimuoviamo muri interni selezionati)
        addLoops(grid, size);

        // Assicuriamo che ci sia un'uscita
        grid[exitX][exitY] = 'E';

        mazeDAO.save(maze);

        // Convertiamo in entità Hibernate
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Tile tile = new Tile(x, y, maze);
                MazeComponent mazeComponent = null;
                if (x == startX && y == startY) {
                    mazeComponent = new StartTile(tile);
                } else if (x == exitX && y == exitY) {
                    mazeComponent = new ExitTile(tile);
                } else if (grid[x][y] == '.') {
                    mazeComponent = new Corridor(tile);
                } else {
                    mazeComponent = new Wall(tile);
                }

                tileDAO.save(tile);
                mazeComponentDAO.save(mazeComponent);

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

    private void addLoops(char[][] grid, int size) {
        int loopsToAdd = (int) (size * size * 0.05); // 5% dei muri trasformati in corridoi
        for (int i = 0; i < loopsToAdd; i++) {
            int x = random.nextInt(size - 2) + 1; // Evita i bordi
            int y = random.nextInt(size - 2) + 1;

            if (grid[x][y] == '#') {
                // Verifica che ci siano due corridoi separati su lati opposti
                if ((grid[x - 1][y] == '.' && grid[x + 1][y] == '.') ||
                        (grid[x][y - 1] == '.' && grid[x][y + 1] == '.')) {
                    grid[x][y] = '.'; // Rimuove il muro creando un loop
                }
            }
        }
    }
}
