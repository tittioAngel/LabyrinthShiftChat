package org.example.model.entities;

import jakarta.persistence.*;
import org.example.model.Maze;
import org.example.model.Player;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "obstacle_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Obstacle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int x;
    private int y;

    @ManyToOne
    @JoinColumn(name = "maze_id", nullable = false)
    private Maze maze;

    private boolean activated = false;

    public Obstacle() { }

    public Obstacle(int x, int y, Maze maze) {
        this.x = x;
        this.y = y;
        this.maze = maze;
    }

    // Metodo per applicare l'effetto sul giocatore
    public abstract void applyEffect(Player player);

    // Getters e setters
    public Long getId() { return id; }
    public int getX() { return x; }
    public int getY() { return y; }
    public Maze getMaze() { return maze; }
    public boolean isActivated() { return activated; }
    public void setActivated(boolean activated) { this.activated = activated; }
}
