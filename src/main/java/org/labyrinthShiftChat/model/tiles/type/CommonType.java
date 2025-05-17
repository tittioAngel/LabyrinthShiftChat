package org.labyrinthShiftChat.model.tiles.type;

import org.labyrinthShiftChat.model.Tile;
import org.labyrinthShiftChat.model.tiles.MazeComponent;
import org.labyrinthShiftChat.model.tiles.MazeComponentFactory;
import org.labyrinthShiftChat.model.tiles.common.Corridor;
import org.labyrinthShiftChat.model.tiles.common.ExitTile;
import org.labyrinthShiftChat.model.tiles.common.StartTile;
import org.labyrinthShiftChat.model.tiles.common.Wall;

import java.util.Random;

public enum CommonType {
    CORRIDOR(Corridor::new),
    EXIT_TILE(ExitTile::new),
    START_TILE(StartTile::new),
    WALL(Wall::new);

    private final MazeComponentFactory factory;

    CommonType(MazeComponentFactory factory) {
        this.factory = factory;
    }

    public MazeComponent create(Tile tile) {
        return factory.create(tile);
    }

    public static CommonType random(Random random) {
        CommonType[] values = values();
        return values[random.nextInt(values.length)];
    }
}
