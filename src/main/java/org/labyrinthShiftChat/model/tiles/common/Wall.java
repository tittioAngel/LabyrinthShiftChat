package org.labyrinthShiftChat.model.tiles.common;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import org.labyrinthShiftChat.model.Player;
import org.labyrinthShiftChat.model.Tile;
import org.labyrinthShiftChat.model.tiles.MazeComponent;

@Entity
@DiscriminatorValue("Wall")
@NoArgsConstructor
public class Wall extends MazeComponent {

    public Wall(Tile tile) {
        super(tile, true);
    }

    @Override
    public void triggerEffect(Player player) {

    }
}
