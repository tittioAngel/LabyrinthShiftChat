package org.example.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "tiles")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tile_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Tile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int x;
    private int y;

    @Column(nullable = false)
    private boolean isWalkable=false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maze_id", nullable = false)
    private Maze maze;



    public Tile() {
        this.isWalkable=false;
    } // Costruttore vuoto per Hibernate

    public Tile(int x, int y, boolean isWalkable, Maze maze) {
        this.x = x;
        this.y = y;
        this.isWalkable = isWalkable;
        this.maze = maze;
    }

    // Nuovo costruttore aggiunto
    public Tile(int x, int y, boolean isWalkable) {
        this.x = x;
        this.y = y;
        this.isWalkable = isWalkable;
    }

    public Long getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isWalkable() {
        return isWalkable;
    }

    public Maze getMaze() {
        return maze;
    }



    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWalkable(boolean walkable) {
        isWalkable = walkable;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }


}
