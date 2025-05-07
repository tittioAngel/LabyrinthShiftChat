package org.labyrinthShiftChat.service;

import org.labyrinthShiftChat.dao.*;
import org.labyrinthShiftChat.model.*;
import org.labyrinthShiftChat.model.tiles.common.Corridor;
import org.labyrinthShiftChat.model.tiles.common.ExitTile;
import org.labyrinthShiftChat.model.tiles.common.StartTile;
import org.labyrinthShiftChat.model.tiles.common.Wall;
import org.labyrinthShiftChat.model.tiles.enemy.*;
import org.labyrinthShiftChat.model.tiles.*;
import org.labyrinthShiftChat.model.tiles.obstacle.FreezingFog;
import org.labyrinthShiftChat.model.tiles.obstacle.Slime;
import org.labyrinthShiftChat.model.tiles.obstacle.Thorns;
import org.labyrinthShiftChat.model.tiles.obstacle.TimeVortex;
import org.labyrinthShiftChat.model.tiles.powerUp.Sprint;
import org.labyrinthShiftChat.model.tiles.powerUp.SightOrb;
import org.labyrinthShiftChat.model.tiles.powerUp.ObstacleNullifier;
import org.labyrinthShiftChat.util.MazeGenerator;

import java.util.List;
import java.util.Random;

public class MazeService {

    private final TileService tileService = new TileService();

    private final MazeDAO mazeDAO = new MazeDAO();
    private final TileDAO tileDAO = new TileDAO();
    private final MazeComponentDAO mazeComponentDAO = new MazeComponentDAO();

    private final MazeGenerator mazeGenerator = new MazeGenerator(); //  Usa l'algoritmo DFS

    public Maze generateRandomMaze(DifficultyLevel difficulty) {
        Maze maze = mazeGenerator.generateMaze(difficulty);
        addMazeComponents(maze);

        return maze;
    }

    public MazeComponent findMazeComponentByTile(Tile tile) {
        return mazeComponentDAO.findByTile(tile);
    }

    public char[][] displayMaze(Maze maze) {
        List<Tile> tiles = tileDAO.findAllTilesByMaze(maze.getId());
        int size = maze.getDifficulty().getMazeSize();
        char[][] grid = new char[size][size];

        // Inizializza la griglia con caratteri vuoti
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                grid[x][y] = ' ';
            }
        }

        for (Tile tile : tiles) {
            MazeComponent component = findMazeComponentByTile(tile);
            if (component instanceof Wall) {
                grid[tile.getX()][tile.getY()] = '#';  // Muri come #
            } else if (component instanceof Corridor) {
                grid[tile.getX()][tile.getY()] = '.';  // Corridoi come .
            } else if (component instanceof ExitTile) {
                grid[tile.getX()][tile.getY()] = 'E';  // Uscita come E
            } else if (component instanceof StartTile) {
                grid[tile.getX()][tile.getY()] = 'S';  // Inizio come S
            } else if (component instanceof IceCyclops || component instanceof PhantomHorse || component instanceof ShadowDemon) {
                grid[tile.getX()][tile.getY()] = 'N';
            } else if (component instanceof FreezingFog || component instanceof Slime || component instanceof Thorns || component instanceof TimeVortex) {
                grid[tile.getX()][tile.getY()] = 'O';
            } else if (component instanceof Sprint || component instanceof SightOrb || component instanceof ObstacleNullifier) {
                grid[tile.getX()][tile.getY()] = 'P';
            }
        }

        return grid;
    }

    public char[][] createLimitedView(Maze maze, int playerX, int playerY) {
        List<Tile> tiles = tileDAO.findAllTilesByMaze(maze.getId());
        int size = maze.getDifficulty().getMazeSize();
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
                MazeComponent mazeComponent = mazeComponentDAO.findByTile(tile);
                if (mazeComponent instanceof Wall) {
                    grid[tile.getX()][tile.getY()] = '#'; // Muri
                } else if (mazeComponent instanceof ExitTile) {
                    grid[tile.getX()][tile.getY()] = 'E'; // Uscita
                } else if (mazeComponent instanceof StartTile) {
                    grid[tile.getX()][tile.getY()] = 'S'; // Posizione di partenza
                } else {
                    grid[tile.getX()][tile.getY()] = '.';
                }
            }
        }
        // Segna la posizione del giocatore con 'G'
        grid[playerX][playerY] = 'G';

        return grid;
    }


    private void addMazeComponents(Maze maze) {
        List<Tile> allTiles = tileDAO.findAllTilesByMaze(maze.getId());

        List<Tile> availableTiles = tileService.findAllAvailableTiles(allTiles);

        Random random = new Random();

        int obstacleCount = maze.getDifficulty().getObstacleCount();
        int enemyCount = maze.getDifficulty().getEnemyCount();
        int powerUpCount = maze.getDifficulty().getPowerUpCount();

        // Posizionamento degli "Ostacoli"
        for (int i = 0; i < obstacleCount; i++) {
            int randomTileIndex = random.nextInt(availableTiles.size());
            Tile selectedTile = availableTiles.get(randomTileIndex);

            int obstacleType = random.nextInt(4);
            MazeComponent obstacleEntity = null;

            switch (obstacleType) {
                case 0:
                    obstacleEntity = new Thorns(selectedTile);
                    break;
                case 1:
                    obstacleEntity = new FreezingFog(selectedTile);
                    break;
                case 2:
                    obstacleEntity = new Slime(selectedTile);
                    break;
                case 3:
                    obstacleEntity = new TimeVortex(selectedTile);
                    break;
            }

            mazeComponentDAO.merge(obstacleEntity);
            availableTiles.remove(randomTileIndex);
        }

        // Posizionamento dei "Nemici"
        for (int i = 0; i < enemyCount; i++) {
            int randomTileIndex = random.nextInt(availableTiles.size());
            Tile selectedTile = availableTiles.get(randomTileIndex);

            int enemyType = random.nextInt(3);
            MazeComponent enemyEntity = null;
            switch (enemyType) {
                case 0:
                    enemyEntity = new PhantomHorse(selectedTile);
                    break;
                case 1:
                    enemyEntity = new ShadowDemon(selectedTile);
                    break;
                case 2:
                    enemyEntity = new IceCyclops(selectedTile);
                    break;
            }

            mazeComponentDAO.merge(enemyEntity);
            availableTiles.remove(randomTileIndex);
        }

        // Posizionamento dei "PowerUp"
        for (int i = 0; i < powerUpCount; i++) {
            int randomTileIndex = random.nextInt(availableTiles.size());
            Tile selectedTile = availableTiles.get(randomTileIndex);

            int powerUpType = random.nextInt(3);
            MazeComponent powerUpEntity = null;
            switch (powerUpType) {
                case 0:
                    powerUpEntity = new Sprint(selectedTile);
                    break;
                case 1:
                    powerUpEntity = new SightOrb(selectedTile);
                    break;
                case 2:
                    powerUpEntity = new ObstacleNullifier(selectedTile);
                    break;
            }

            mazeComponentDAO.merge(powerUpEntity);
            availableTiles.remove(randomTileIndex);
        }
    }

    // ✅ Metodo per ottenere la posizione di partenza del giocatore
    public StartTile getStartTile(Maze maze) {
        List<Tile> tiles = tileDAO.findAllTilesByMaze(maze.getId());

        for (Tile tile : tiles) {
            MazeComponent mazeComponent = mazeComponentDAO.findByTile(tile);
            if (mazeComponent instanceof StartTile) {
                return (StartTile) mazeComponent;
            }
        }

        throw new IllegalStateException("Nessuna posizione di partenza trovata nel labirinto!");
    }

    public char[][] createPreviewMiniMaze(Maze maze) {
        System.out.println("🔍 Visualizzazione completa del labirinto per " + maze.getDifficulty().getPreviewTime() + " secondi:");

        // mi faccio ridare la griglia
         return displayMaze(maze);

    }


}
