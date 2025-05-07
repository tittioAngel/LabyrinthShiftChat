package org.labyrinthShiftChat.model.tiles.obstacle;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import org.labyrinthShiftChat.model.Player;
import org.labyrinthShiftChat.model.Tile;
import org.labyrinthShiftChat.model.tiles.MazeComponent;

@Entity
@DiscriminatorValue("TimeVortex")
@NoArgsConstructor
public class TimeVortex extends MazeComponent {

    public TimeVortex(Tile tile) {
        super(tile, true);
    }

    @Override
    public void triggerEffect(Player player) {
        if (!player.isNextObstacleIgnored()) {
            System.out.println("Hai attivato il Vortice Temporale! Verrai spostato indietro di 2 tile.");
            int[] previousPos = player.getPreviousPosition(2);
            player.setPosition(previousPos[0], previousPos[1]);
            player.setNextObstacleIgnored(false);
        } else {
            System.out.println("Sei nel Vortice Temporale! Non avrà alcun effetto, utilizzi il Disattivatore di Ostacoli! ");
        }

    }

}
