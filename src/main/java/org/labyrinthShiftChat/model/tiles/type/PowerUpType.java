package org.labyrinthShiftChat.model.tiles.type;

import org.labyrinthShiftChat.model.Tile;
import org.labyrinthShiftChat.model.tiles.MazeComponent;
import org.labyrinthShiftChat.model.tiles.MazeComponentFactory;
import org.labyrinthShiftChat.model.tiles.powerUp.ObstacleNullifier;
import org.labyrinthShiftChat.model.tiles.powerUp.SightOrb;
import org.labyrinthShiftChat.model.tiles.powerUp.Sprint;

import java.util.Random;

public enum PowerUpType {
    OBSTACLE_NULLIFIER(ObstacleNullifier::new),
    SIGHT_ORB(SightOrb::new),
    SPRINT(Sprint::new);

    private final MazeComponentFactory factory;

    PowerUpType(MazeComponentFactory factory) {
        this.factory = factory;
    }

    public MazeComponent create(Tile tile) {
        return factory.create(tile);
    }

    public static PowerUpType random(Random random) {
        PowerUpType[] values = values();
        return values[random.nextInt(values.length)];
    }
}
