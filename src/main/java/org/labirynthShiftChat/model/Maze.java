package org.labirynthShiftChat.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mazes")
public class Maze {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int level;

    @OneToMany(mappedBy = "maze", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Tile> tiles = new ArrayList<>();  // ✅ Lista di tile

    private int size;



    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficulty;

    public Maze() {} // Costruttore vuoto per Hibernate

    public Maze(DifficultyLevel difficulty) {
        this.size = difficulty.getMazeSize(); // ✅ Impostiamo la dimensione dal livello di difficoltà
        this.difficulty = difficulty;
    }

    public Maze(int level) {
        this.level = level;
    }

    public int getSize() { return size; }

    public Long getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void addTile(Tile tile) {  // ✅ Metodo per aggiungere tile
        tiles.add(tile);
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public DifficultyLevel getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(DifficultyLevel difficulty) {
        this.difficulty = difficulty;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
