package org.labirynthShiftChat.model.tiles;

import jakarta.persistence.*;
import org.labirynthShiftChat.model.Maze;
import org.labirynthShiftChat.model.Tile;

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
