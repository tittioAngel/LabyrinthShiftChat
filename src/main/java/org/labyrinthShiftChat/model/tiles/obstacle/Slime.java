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
        System.out.println("Hai attivato lo Slime! Sei immobilizzato per " + getEffectDuration() + "secondi.");
        try {
            Thread.sleep(getEffectDuration() * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
