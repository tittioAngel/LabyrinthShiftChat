package org.example.model;

import jakarta.persistence.*;

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
