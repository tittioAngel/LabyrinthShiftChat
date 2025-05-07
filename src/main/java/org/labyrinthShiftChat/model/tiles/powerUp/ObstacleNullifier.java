package org.labyrinthShiftChat.model.tiles.powerUp;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import org.labyrinthShiftChat.model.Player;
import org.labyrinthShiftChat.model.Tile;
import org.labyrinthShiftChat.model.tiles.MazeComponent;

@Entity
@DiscriminatorValue("ObstacleNullifier")
@NoArgsConstructor
public class ObstacleNullifier extends MazeComponent {

    public ObstacleNullifier(Tile tile) {
        super(tile, true);
    }

    @Override
    public void triggerEffect(Player player) {
        System.out.println("Hai raccolto il Disattivatore di Ostacoli! Il prossimo OSTACOLO non avrà alcun effetto!");
        player.setNextObstacleIgnored(true);
    }
}
