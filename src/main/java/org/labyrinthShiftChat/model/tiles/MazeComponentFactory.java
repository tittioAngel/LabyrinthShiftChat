package org.labyrinthShiftChat.model.tiles;

import org.labyrinthShiftChat.model.Tile;

public interface MazeComponentFactory {
    MazeComponent create(Tile tile);
}

