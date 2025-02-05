package org.example.model.tiles;

import jakarta.persistence.*;
import org.example.model.Maze;
import org.example.model.Tile;

@Entity
@DiscriminatorValue("ExitTile")
public class ExitTile extends Tile {
    public ExitTile() {
        super();
        this.setWalkable(true); // ✅ Il giocatore può attraversarla
    }

    public ExitTile(int x, int y, Maze maze) {
        super(x, y, true, maze);
    }
}
