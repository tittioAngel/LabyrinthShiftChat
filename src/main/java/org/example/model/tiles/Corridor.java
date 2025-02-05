package org.example.model.tiles;

import jakarta.persistence.*;
import org.example.model.Maze;
import org.example.model.Tile;

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
