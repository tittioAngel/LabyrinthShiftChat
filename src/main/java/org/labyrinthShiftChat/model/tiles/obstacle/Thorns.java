package org.labyrinthShiftChat.model.tiles.obstacle;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.labyrinthShiftChat.model.Player;
import org.labyrinthShiftChat.model.Tile;
import org.labyrinthShiftChat.model.tiles.MazeComponent;

@Entity
@DiscriminatorValue("Thorns")
@Getter
@NoArgsConstructor
public class Thorns extends MazeComponent {

    private final int effectDuration = 5;

    public Thorns(Tile tile) {
        super(tile, true);
    }

    @Override
    public void triggerEffect(Player player) {
        if (!player.isNextObstacleIgnored()) {
            System.out.println("\n🚧 Hai attivato le Spine! La tua velocità è ridotta del 50% per " + getEffectDuration() + " secondi.");
            player.applySpeedEffect(getEffectDuration(), 0.5);
            player.setNextObstacleIgnored(false);
        } else {
            System.out.println("\n🚧 Sei nelle Spine! Non avrà alcun effetto, utilizzi il Disattivatore di Ostacoli! ");
        }
    }
}
