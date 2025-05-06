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
        System.out.println("Hai attivato la Nebbia Gelida! La tua velocità è ridotta di un terzo per " + getEffectDuration() + " secondi.");
        // Logica per rallentare il giocatore
        //player.setSpeed(player.getSpeed()/3);
        player.applySpeedEffect(getEffectDuration(), 0.33);
    }
}
