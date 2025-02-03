package org.example.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Corridor")
public class Corridor extends Tile {
    public Corridor() {
        super();
        this.setWalkable(true);
    }

    public Corridor(int x, int y, Maze maze) {
        super(x, y, true, maze); // ✅ Corridor È attraversabile
    }
}
