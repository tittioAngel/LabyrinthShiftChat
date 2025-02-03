package org.example.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Obstacle")
public class Obstacle extends Tile {
    private String type;

    public Obstacle() {}

    public Obstacle(String type, int x, int y, boolean isWalkable, Maze maze) {
        super(x, y, isWalkable, maze); // ✅ Definiamo se l'ostacolo è attraversabile o meno
        this.type = type;
    }

    public String getType() { return type; }
}
