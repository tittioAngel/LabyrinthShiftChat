package org.example.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Wall")
public class Wall extends Tile {
    public Wall() {
        super();
        this.setWalkable(false);
    }

    public Wall(int x, int y, Maze maze) {
        super(x, y, false, maze); // ✅ Wall NON è attraversabile
    }
}
