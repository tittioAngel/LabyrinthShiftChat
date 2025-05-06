package org.labyrinthShiftChat.model.tiles.powerUp;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import org.labyrinthShiftChat.model.Player;
import org.labyrinthShiftChat.model.Tile;
import org.labyrinthShiftChat.model.tiles.MazeComponent;

@Entity
@DiscriminatorValue("ChronoCrystal")
@NoArgsConstructor
public class ChronoCrystal extends MazeComponent {

    public ChronoCrystal(Tile tile) {
        super(tile, true);
    }

    @Override
    public void triggerEffect(Player player) {

    }
}
