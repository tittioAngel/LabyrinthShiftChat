package org.labyrinthShiftChat.model.tiles.enemy;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.labyrinthShiftChat.model.Player;
import org.labyrinthShiftChat.model.Tile;
import org.labyrinthShiftChat.model.tiles.MazeComponent;

@Entity
@DiscriminatorValue("IceCyclops")
@Getter
@NoArgsConstructor
public class IceCyclops extends MazeComponent {

    private final int effectDuration = 5;

    public IceCyclops(Tile tile) {
        super(tile, true);
    }

    @Override
    public void triggerEffect(Player player) {
        System.out.println("❄️ Un Ice Cyclops genera Nebbia Gelida! La tua velocità è ridotta per " + getEffectDuration() + " secondi.");
        //player.setSpeed(player.getSpeed()/3);
        player.applySpeedEffect(getEffectDuration(),0.33);
    }
}
