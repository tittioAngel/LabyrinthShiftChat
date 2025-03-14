package org.labyrinthShiftChat.model.tiles;

import jakarta.persistence.*;
import org.labyrinthShiftChat.model.Maze;
import org.labyrinthShiftChat.model.Tile;

@Entity
@DiscriminatorValue("StartTile")
public class StartTile extends Tile {
    public StartTile() {
        super();
        this.setWalkable(true); // ✅ Il giocatore può muoversi
    }

    public StartTile(int x, int y, Maze maze) {
        super(x, y, true, maze);
    }
}
