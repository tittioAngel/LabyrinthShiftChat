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
        System.out.println("Hai attivato le Spine! La tua velocità è ridotta del 50% per " + getEffectDuration() + " secondi.");
        //player.setSpeed(player.getSpeed()/2);
        player.applySpeedEffect(getEffectDuration(), 0.5);
    }
}
