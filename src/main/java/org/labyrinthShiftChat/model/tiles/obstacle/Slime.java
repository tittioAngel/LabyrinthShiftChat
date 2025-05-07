package org.labyrinthShiftChat.model.tiles.obstacle;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.labyrinthShiftChat.model.Player;
import org.labyrinthShiftChat.model.Tile;
import org.labyrinthShiftChat.model.tiles.MazeComponent;

@Entity
@DiscriminatorValue("Slime")
@Getter
@NoArgsConstructor
public class Slime extends MazeComponent {

    private final int effectDuration = 2;

    public Slime(Tile tile) {
        super(tile, true);
    }

    @Override
    public void triggerEffect(Player player) {
        if (!player.isNextObstacleIgnored()) {
            System.out.println("Hai attivato lo Slime! Sei immobilizzato per " + getEffectDuration() + "secondi.");
            try {
                Thread.sleep(getEffectDuration() * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            player.setNextObstacleIgnored(false);
        } else {
            System.out.println("Sei nello Slime! Non avrà alcun effetto, utilizzi il Disattivatore di Ostacoli! ");
        }
    }
}
