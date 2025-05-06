package org.labyrinthShiftChat.model.tiles.enemy;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import org.labyrinthShiftChat.model.Player;
import org.labyrinthShiftChat.model.Tile;
import org.labyrinthShiftChat.model.tiles.MazeComponent;

@Entity
@DiscriminatorValue("PhantomHorse")
@NoArgsConstructor
public class PhantomHorse extends MazeComponent {

    public PhantomHorse(Tile tile) {
        super(tile, true);
    }

    @Override
    public void triggerEffect(Player player) {
        System.out.println("Un Phantom Horse ti ha colpito! Verrai spinto indietro di 4 tile.");
        int[] previousPos = player.getPreviousPosition(4);
        player.setPosition(previousPos[0], previousPos[1]);
    }
}
