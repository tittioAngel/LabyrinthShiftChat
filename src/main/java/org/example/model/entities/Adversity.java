package org.example.model.entities;

import jakarta.persistence.*;
import org.example.model.Maze;
import org.example.model.Player;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "adversity_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Adversity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int x;
    private int y;

    @ManyToOne
    @JoinColumn(name = "maze_id", nullable = false)
    private Maze maze;

    @Enumerated(EnumType.STRING)
    private AdversityType adversityType;

    private boolean activated = false;

    public Adversity() { }

    public Adversity(int x, int y, Maze maze, AdversityType adversityType) {
        this.x = x;
        this.y = y;
        this.maze = maze;
        this.adversityType = adversityType;
    }

    /**
     * Metodo astratto che, in base al tipo di adversità, applica l'effetto sul giocatore.
     * Le sottoclassi implementeranno questo metodo per definire il comportamento specifico.
     */
    public abstract void triggerEffect(Player player);

    // Getters e setters
    public Long getId() { return id; }
    public int getX() { return x; }
    public int getY() { return y; }
    public Maze getMaze() { return maze; }
    public AdversityType getAdversityType() { return adversityType; }
    public boolean isActivated() { return activated; }

    public void setActivated(boolean activated) { this.activated = activated; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
}
