package org.example.service;

import org.example.dao.EnemyDAO;
import org.example.dao.MazeDAO;
import org.example.dao.ObstacleDAO;
import org.example.dao.TileDAO;
import org.example.model.*;
import org.example.model.entities.*;
import org.example.model.tiles.*;

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
        addObstaclesAndEnemies(maze);

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

    private void addObstaclesAndEnemies(Maze maze) {
        TileDAO tileDAO = new TileDAO();
        List<Tile> allTiles = tileDAO.findAllTilesByMaze(maze.getId());
        // Seleziona solo i tile di tipo Corridor escludendo start ed exit
        List<Tile> availableTiles = allTiles.stream()
                .filter(tile -> tile instanceof Corridor)
                .filter(tile -> !(tile instanceof StartTile) && !(tile instanceof ExitTile))
                .collect(Collectors.toList());

        Random random = new Random();
        ObstacleDAO obstacleDAO = new ObstacleDAO();
        EnemyDAO enemyDAO = new EnemyDAO();

        // Generazione degli ostacoli
        int obstacleCount = maze.getDifficulty().getObstacleCount();
        for (int i = 0; i < obstacleCount && !availableTiles.isEmpty(); i++) {
            int index = random.nextInt(availableTiles.size());
            Tile tile = availableTiles.get(index);
            // Seleziona casualmente uno dei 4 tipi di ostacolo
            int obstacleType = random.nextInt(4);
            Obstacle obstacle = null;
            switch (obstacleType) {
                case 0:
                    obstacle = new Thorns(tile.getX(), tile.getY(), maze);
                    break;
                case 1:
                    obstacle = new FreezingFog(tile.getX(), tile.getY(), maze);
                    break;
                case 2:
                    obstacle = new Slime(tile.getX(), tile.getY(), maze);
                    break;
                case 3:
                    obstacle = new TimeVortex(tile.getX(), tile.getY(), maze);
                    break;
            }
            if (obstacle != null) {
                obstacleDAO.save(obstacle);
            }
            // Rimuove il tile per non posizionare più elementi sulla stessa casella
            availableTiles.remove(index);
        }

        // Per gli enemy, ripristiniamo la lista dei tile disponibili
        availableTiles = allTiles.stream()
                .filter(tile -> tile instanceof Corridor)
                .filter(tile -> !(tile instanceof StartTile) && !(tile instanceof ExitTile))
                .collect(Collectors.toList());

        int enemyCount = maze.getDifficulty().getEnemyCount();
        for (int i = 0; i < enemyCount && !availableTiles.isEmpty(); i++) {
            int index = random.nextInt(availableTiles.size());
            Tile tile = availableTiles.get(index);
            int enemyType = random.nextInt(3);
            Enemy enemy = null;
            switch (enemyType) {
                case 0:
                    enemy = new PhantomHorse(tile.getX(), tile.getY(), maze);
                    break;
                case 1:
                    enemy = new ShadowDemon(tile.getX(), tile.getY(), maze);
                    break;
                case 2:
                    enemy = new IceCyclops(tile.getX(), tile.getY(), maze);
                    break;
            }
            if (enemy != null) {
                enemyDAO.save(enemy);
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

    /**
     * Controlla se, nella posizione (x, y) del labirinto, sono presenti ostacoli ed enemy.
     * Se presenti, ne attiva l'effetto sul giocatore passato come parametro.
     */
    public void checkTileEffects(Maze maze, int x, int y, Player player) {
        ObstacleDAO obstacleDAO = new ObstacleDAO();
        EnemyDAO enemyDAO = new EnemyDAO();

        // Recupera tutti gli ostacoli associati al labirinto e filtra quelli che si trovano nella posizione (x, y)
        List<Obstacle> obstacles = obstacleDAO.findAllObstaclesByMaze(maze.getId())
                .stream()
                .filter(o -> o.getX() == x && o.getY() == y)
                .collect(Collectors.toList());

        for (Obstacle obstacle : obstacles) {
            if (!obstacle.isActivated()) {
                obstacle.applyEffect(player);
                obstacle.setActivated(true);
                // Se necessario, si può aggiornare lo stato dell'ostacolo sul DB
            }
        }

        // Recupera tutti gli enemy associati al labirinto e filtra quelli presenti in (x, y)
        List<Enemy> enemies = enemyDAO.findAllEnemiesByMaze(maze.getId())
                .stream()
                .filter(e -> e.getX() == x && e.getY() == y)
                .collect(Collectors.toList());

        for (Enemy enemy : enemies) {
            enemy.attack(player);
            // Se necessario, si può decidere di rimuovere o marcare l'enemy dopo l'attacco
        }
    }
}
