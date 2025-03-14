package org.labirynthShiftChat.model.tiles;

import jakarta.persistence.*;
import org.labirynthShiftChat.model.Maze;
import org.labirynthShiftChat.model.Tile;

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
