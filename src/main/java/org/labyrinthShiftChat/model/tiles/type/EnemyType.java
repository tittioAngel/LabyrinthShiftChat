package org.labyrinthShiftChat.model.tiles.type;

import org.labyrinthShiftChat.model.Tile;
import org.labyrinthShiftChat.model.tiles.MazeComponent;
import org.labyrinthShiftChat.model.tiles.MazeComponentFactory;
import org.labyrinthShiftChat.model.tiles.enemy.IceCyclops;
import org.labyrinthShiftChat.model.tiles.enemy.PhantomHorse;
import org.labyrinthShiftChat.model.tiles.enemy.ShadowDemon;

import java.util.Random;

public enum EnemyType {
    ICE_CYCLOPS(IceCyclops::new),
    PHANTOM_HORSE(PhantomHorse::new),
    SHADOW_DEMON(ShadowDemon::new);

    private final MazeComponentFactory factory;

    EnemyType(MazeComponentFactory factory) {
        this.factory = factory;
    }

    public MazeComponent create(Tile tile) {
        return factory.create(tile);
    }

    public static EnemyType random(Random random) {
        EnemyType[] values = values();
        return values[random.nextInt(values.length)];
    }
}
