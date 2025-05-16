package org.labyrinthShiftChat.model.tiles.type;

import org.labyrinthShiftChat.model.Tile;
import org.labyrinthShiftChat.model.tiles.MazeComponent;
import org.labyrinthShiftChat.model.tiles.MazeComponentFactory;
import org.labyrinthShiftChat.model.tiles.obstacle.FreezingFog;
import org.labyrinthShiftChat.model.tiles.obstacle.Slime;
import org.labyrinthShiftChat.model.tiles.obstacle.Thorns;
import org.labyrinthShiftChat.model.tiles.obstacle.TimeVortex;

import java.util.Random;

public enum ObstacleType {
    THORNS(Thorns::new),
    FREEZING_FOG(FreezingFog::new),
    SLIME(Slime::new),
    TIME_VORTEX(TimeVortex::new);

    private final MazeComponentFactory factory;

    ObstacleType(MazeComponentFactory factory) {
        this.factory = factory;
    }

    public MazeComponent create(Tile tile) {
        return factory.create(tile);
    }

    public static ObstacleType random(Random random) {
        ObstacleType[] values = values();
        return values[random.nextInt(values.length)];
    }
}
