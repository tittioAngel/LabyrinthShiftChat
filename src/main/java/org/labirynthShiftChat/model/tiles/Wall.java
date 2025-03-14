package org.labirynthShiftChat.model.tiles;

import jakarta.persistence.*;
import org.labirynthShiftChat.model.Maze;
import org.labirynthShiftChat.model.Tile;

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
