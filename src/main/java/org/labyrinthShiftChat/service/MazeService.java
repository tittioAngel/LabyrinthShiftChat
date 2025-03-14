package org.labyrinthShiftChat.service;

import org.labyrinthShiftChat.dao.*;
import org.labyrinthShiftChat.model.*;
import org.labyrinthShiftChat.model.entities.*;
import org.labyrinthShiftChat.model.tiles.*;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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

        // Aggiungiamo ostacoli ed enemy
        addAdversities(maze);

//        System.out.println("✅ Minimaze di livello " + difficulty.name() + " generato con successo!");
//        System.out.println("📍 Posizione di partenza: (0, 0)");
//        System.out.println("🏁 Uscita posizionata in (" + (difficulty.getMazeSize() - 1) + ", " + (difficulty.getMazeSize() - 1) + ")");

        return maze;
    }

    public void displayMaze(Maze maze) {
        List<Tile> tiles = tileDAO.findAllTilesByMaze(maze.getId());
        List<Adversity> adversities = new AdversityDAO().findAllByMaze(maze.getId());
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

        // Posiziona i nemici e ostacoli
        for (Adversity adversity : adversities) {
            if (adversity.getAdversityType() == AdversityType.ENEMY) {
                grid[adversity.getX()][adversity.getY()] = 'N'; // Nemici
            } else {
                grid[adversity.getX()][adversity.getY()] = 'O'; // Ostacoli
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

    private void addAdversities(Maze maze) {
        TileDAO tileDAO = new TileDAO();
        List<Tile> allTiles = tileDAO.findAllTilesByMaze(maze.getId());
        // Selezioniamo solo i tile di tipo Corridor, escludendo start ed exit
        List<Tile> availableTiles = allTiles.stream()
                .filter(tile -> tile instanceof Corridor)
                .filter(tile -> !(tile instanceof StartTile) && !(tile instanceof ExitTile))
                .collect(Collectors.toList());

        Random random = new Random();
        AdversityDAO adversityDAO = new AdversityDAO();

        // Posizionamento degli "ostacoli"
        int obstacleCount = maze.getDifficulty().getObstacleCount();
        for (int i = 0; i < obstacleCount && !availableTiles.isEmpty(); i++) {
            int index = random.nextInt(availableTiles.size());
            Tile tile = availableTiles.get(index);
            int obstacleType = random.nextInt(4);
            Adversity adversity = null;
            switch (obstacleType) {
                case 0:
                    adversity = new Thorns(tile.getX(), tile.getY(), maze);
                    break;
                case 1:
                    adversity = new FreezingFog(tile.getX(), tile.getY(), maze);
                    break;
                case 2:
                    adversity = new Slime(tile.getX(), tile.getY(), maze);
                    break;
                case 3:
                    adversity = new TimeVortex(tile.getX(), tile.getY(), maze);
                    break;
            }
            if (adversity != null) {
                adversityDAO.save(adversity);
            }
            availableTiles.remove(index);
        }

        // Ripristiniamo la lista dei tile disponibili per i nemici
        availableTiles = allTiles.stream()
                .filter(tile -> tile instanceof Corridor)
                .filter(tile -> !(tile instanceof StartTile) && !(tile instanceof ExitTile))
                .collect(Collectors.toList());

        int enemyCount = maze.getDifficulty().getEnemyCount();
        for (int i = 0; i < enemyCount && !availableTiles.isEmpty(); i++) {
            int index = random.nextInt(availableTiles.size());
            Tile tile = availableTiles.get(index);
            int enemyType = random.nextInt(3);
            Adversity adversity = null;
            switch (enemyType) {
                case 0:
                    adversity = new PhantomHorse(tile.getX(), tile.getY(), maze);
                    break;
                case 1:
                    adversity = new ShadowDemon(tile.getX(), tile.getY(), maze);
                    break;
                case 2:
                    adversity = new IceCyclops(tile.getX(), tile.getY(), maze);
                    break;
            }
            if (adversity != null) {
                adversityDAO.save(adversity);
            }
            availableTiles.remove(index);
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

    public void previewMiniMaze(Maze maze) {
        System.out.println("🔍 Visualizzazione completa del labirinto per " + maze.getDifficulty().getPreviewTime() + " secondi:");

        // Mostriamo il labirinto intero
        displayMaze(maze);

        try {
            Thread.sleep(maze.getDifficulty().getPreviewTime() * 1000L); // Attesa per la previsualizzazione
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("⏳ Previsualizzazione terminata, il gioco sta per iniziare...");
    }


}
