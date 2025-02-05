package org.example.service;

import org.example.dao.MazeDAO;
import org.example.dao.TileDAO;
import org.example.model.*;
import org.example.model.tiles.Corridor;
import org.example.model.tiles.ExitTile;
import org.example.model.tiles.StartTile;
import org.example.model.tiles.Wall;

import java.util.List;

public class MazeService {
    private final MazeDAO mazeDAO = new MazeDAO();
    private final TileDAO tileDAO = new TileDAO();
    private final MazeGenerator mazeGenerator = new MazeGenerator(); // ✅ Usa l'algoritmo DFS

    public Maze generateRandomMaze(DifficultyLevel difficulty) {
        // Genera un minimaze risolvibile con un percorso valido
        Maze maze = mazeGenerator.generateMaze(difficulty);
        mazeDAO.save(maze);

        // Salva ogni tile nel database
        for (Tile tile : maze.getTiles()) {
            tile.setMaze(maze);// Assicura che la tile sia collegata a un Maze già salvato
            tileDAO.merge(tile);
        }

        System.out.println("✅ Minimaze di livello " + difficulty.name() + " generato con successo!");
        System.out.println("📍 Posizione di partenza: (0, 0)");
        System.out.println("🏁 Uscita posizionata in (" + (difficulty.getMazeSize() - 1) + ", " + (difficulty.getMazeSize() - 1) + ")");

        return maze;
    }

    public void displayMaze(Maze maze) {
        List<Tile> tiles = tileDAO.findAllTilesByMaze(maze.getId());
        int size = maze.getSize();
        char[][] grid = new char[size][size];

        // Inizializza la griglia con caratteri vuoti
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                grid[x][y] = ' ';
            }
        }

        // Riempie la griglia con le tile
        for (Tile tile : tiles) {
            if (tile instanceof Wall) {
                grid[tile.getX()][tile.getY()] = '#';  // Muri come #
            } else if (tile instanceof Corridor) {
                grid[tile.getX()][tile.getY()] = '.';  // Corridoi come .
            } else if (tile instanceof ExitTile) {
                grid[tile.getX()][tile.getY()] = 'E';  // Uscita come E
            } else if (tile instanceof StartTile) {
                grid[tile.getX()][tile.getY()] = 'S';  // Inizio come S
            }
        }

        // Stampa la griglia
        System.out.println("\n🔍 Pre-visualizzazione del labirinto:");
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                System.out.print(grid[x][y] + " ");
            }
            System.out.println();
        }
    }

    public void displayLimitedView(Maze maze, int playerX, int playerY) {
        List<Tile> tiles = tileDAO.findAllTilesByMaze(maze.getId());
        int size = maze.getSize();
        char[][] grid = new char[size][size];

        // Riempie la griglia con spazi vuoti (celle non visibili)
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                grid[x][y] = ' ';
            }
        }

        // Mostra solo le caselle adiacenti al giocatore
        for (Tile tile : tiles) {
            if (Math.abs(tile.getX() - playerX) <= 1 && Math.abs(tile.getY() - playerY) <= 1) {
                if (tile instanceof Wall) {
                    grid[tile.getX()][tile.getY()] = '#'; // Muri
                } else if (tile instanceof Corridor) {
                    grid[tile.getX()][tile.getY()] = '.'; // Corridoi
                } else if (tile instanceof ExitTile) {
                    grid[tile.getX()][tile.getY()] = 'E'; // Uscita
                } else if (tile instanceof StartTile) {
                    grid[tile.getX()][tile.getY()] = 'S'; // Posizione di partenza
                }
            }
        }
        // Segna la posizione del giocatore con 'G'
        grid[playerX][playerY] = 'G';

        // Stampa la griglia limitata
        System.out.println("\n👀 Vista limitata del labirinto:");
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                System.out.print(grid[x][y] + " ");
            }
            System.out.println();
        }
    }


    // ✅ Metodo per controllare se la mossa è valida
    public boolean isValidMove(Maze maze, int x, int y) {
        List<Tile> tiles = tileDAO.findAllTilesByMaze(maze.getId());

        // Controlla se la posizione è dentro i limiti della mappa
        if (x < 0 || y < 0 || x >= maze.getSize() || y >= maze.getSize()) {
            return false;
        }

        // Cerca la tile alla posizione x, y
        for (Tile tile : tiles) {
            if (tile.getX() == x && tile.getY() == y) {
                return !(tile instanceof Wall); // Se è un muro, il movimento non è valido
            }
        }

        return false; // Se non c'è una tile valida, il movimento non è consentito
    }

    // ✅ Metodo per controllare se il giocatore ha raggiunto l'uscita
    public boolean isExit(Maze maze, int x, int y) {
        List<Tile> tiles = tileDAO.findAllTilesByMaze(maze.getId());

        for (Tile tile : tiles) {
            if (tile.getX() == x && tile.getY() == y && tile instanceof ExitTile) {
                return true; // Il giocatore ha raggiunto l'uscita
            }
        }

        return false;
    }

    // ✅ Metodo per ottenere la posizione di partenza del giocatore
    public StartTile getStartTile(Maze maze) {
        List<Tile> tiles = tileDAO.findAllTilesByMaze(maze.getId());

        for (Tile tile : tiles) {
            if (tile instanceof StartTile) {
                return (StartTile) tile;
            }
        }

        throw new IllegalStateException("Nessuna posizione di partenza trovata nel labirinto!");
    }
}
