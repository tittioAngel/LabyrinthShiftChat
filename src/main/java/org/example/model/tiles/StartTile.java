package org.example.model.tiles;

import jakarta.persistence.*;
import org.example.model.Maze;
import org.example.model.Tile;

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
