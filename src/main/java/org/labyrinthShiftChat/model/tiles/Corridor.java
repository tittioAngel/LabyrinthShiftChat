package org.labyrinthShiftChat.model.tiles;

import jakarta.persistence.*;
import org.labyrinthShiftChat.model.Maze;
import org.labyrinthShiftChat.model.Tile;

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
