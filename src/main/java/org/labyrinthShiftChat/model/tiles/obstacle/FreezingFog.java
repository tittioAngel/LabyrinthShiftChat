package org.labyrinthShiftChat.model.tiles.obstacle;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.labyrinthShiftChat.model.Player;
import org.labyrinthShiftChat.model.Tile;
import org.labyrinthShiftChat.model.tiles.MazeComponent;

@Entity
@DiscriminatorValue("FreezingFog")
@Getter
@NoArgsConstructor
public class FreezingFog extends MazeComponent {

    private final int effectDuration = 7;

    public FreezingFog(Tile tile) {
        super(tile, true);
    }

    @Override
    public void triggerEffect(Player player) {
        if (!player.isNextObstacleIgnored()) {
            System.out.println("Hai attivato la Nebbia Gelida! La tua velocità è ridotta di un terzo per " + getEffectDuration() + " secondi.");
            player.applySpeedEffect(getEffectDuration(), 0.33);
            player.setNextObstacleIgnored(false);
        } else {
            System.out.println("Sei nella Nebbia Gelida! Non avrà alcun effetto, utilizzi il Disattivatore di Ostacoli! ");
        }
    }
}
