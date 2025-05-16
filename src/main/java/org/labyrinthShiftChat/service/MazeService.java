package org.labyrinthShiftChat.service;

import org.labyrinthShiftChat.foundation.*;
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
import org.labyrinthShiftChat.model.tiles.type.EnemyType;
import org.labyrinthShiftChat.model.tiles.type.ObstacleType;
import org.labyrinthShiftChat.model.tiles.type.PowerUpType;
import org.labyrinthShiftChat.util.MazeGenerator;

import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.labyrinthShiftChat.model.tiles.MazeSymbolRegistry.getSymbolForComponent;

public class MazeService {

    private final TileService tileService = new TileService();

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

    private char[][] renderMazeView(Maze maze, Predicate<Tile> visibilityFilter) {
        List<Tile> tiles = tileDAO.findAllTilesByMaze(maze.getId());
        int size = maze.getDifficulty().getMazeSize();
        char[][] grid = new char[size][size];

        for (int x = 0; x < size; x++)
            for (int y = 0; y < size; y++)
                grid[x][y] = ' ';

        for (Tile tile : tiles) {
            if (visibilityFilter.test(tile)) {
                MazeComponent comp = mazeComponentDAO.findByTile(tile); // o da cache se ottimizzi
                grid[tile.getX()][tile.getY()] = getSymbolForComponent(comp);
            }
        }

        return grid;
    }


    public char[][] displayMaze(Maze maze) {
        return renderMazeView(maze, tile -> true);
    }

    public char[][] createLimitedView(Maze maze, int px, int py) {
        char[][] grid = renderMazeView(maze, tile -> Math.abs(tile.getX()-px) <= 1 && Math.abs(tile.getY()-py) <= 1);
        grid[px][py] = 'G';
        return grid;
    }


    private void addMazeComponents(Maze maze) {
        List<Tile> allTiles = tileDAO.findAllTilesByMaze(maze.getId());
        List<Tile> availableTiles = tileService.findAllAvailableTiles(allTiles);
        Random random = new Random();

        int obstacleCount = maze.getDifficulty().getObstacleCount();
        int enemyCount = maze.getDifficulty().getEnemyCount();
        int powerUpCount = maze.getDifficulty().getPowerUpCount();

        placeRandomComponents(availableTiles, obstacleCount, tile -> ObstacleType.random(random).create(tile));
        placeRandomComponents(availableTiles, enemyCount, tile -> EnemyType.random(random).create(tile));
        placeRandomComponents(availableTiles, powerUpCount, tile -> PowerUpType.random(random).create(tile));

    }

    private void placeRandomComponents(List<Tile> availableTiles, int count, Function<Tile, MazeComponent> creator) {
        Random random = new Random();
        for (int i = 0; i < count && !availableTiles.isEmpty(); i++) {
            int index = random.nextInt(availableTiles.size());
            Tile selected = availableTiles.remove(index);
            MazeComponent component = creator.apply(selected);
            mazeComponentDAO.mergeReplacingExisting(component);
        }
    }


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

         return displayMaze(maze);

    }


}
