package org.example.model.entities;

import jakarta.persistence.*;
import org.example.model.Maze;
import org.example.model.Player;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "enemy_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Enemy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int x;
    private int y;

    @ManyToOne
    @JoinColumn(name = "maze_id", nullable = false)
    private Maze maze;

    public Enemy() {}

    public Enemy(int x, int y, Maze maze) {
        this.x = x;
        this.y = y;
        this.maze = maze;
    }

    // Metodo astratto per applicare l'effetto (attacco) sul giocatore
    public abstract void attack(Player player);

    // Getters e setters
    public Long getId() { return id; }
    public int getX() { return x; }
    public int getY() { return y; }
    public Maze getMaze() { return maze; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
}
