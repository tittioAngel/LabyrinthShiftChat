package org.labyrinthShiftChat.model.tiles.powerUp;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.labyrinthShiftChat.model.Player;
import org.labyrinthShiftChat.model.Tile;
import org.labyrinthShiftChat.model.tiles.MazeComponent;

@Entity
@DiscriminatorValue("Sprint")
@Getter
@NoArgsConstructor
public class Sprint extends MazeComponent {

    private final int effectDuration = 4;

    public Sprint(Tile tile) {
        super(tile, true);
    }

    @Override
    public void triggerEffect(Player player) {
        System.out.println("Hai attivato lo Sprint! La tua velocità è raddoppiata per " + getEffectDuration() + " secondi.");
        player.applySpeedEffect(getEffectDuration(), 2);
    }
}
