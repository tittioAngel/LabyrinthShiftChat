package org.labyrinthShiftChat.model.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.labyrinthShiftChat.model.Maze;
import org.labyrinthShiftChat.model.Player;

@Entity
@DiscriminatorValue("IceCyclops")
public class IceCyclops extends Adversity {

    public IceCyclops() {
        super();
    }

    public IceCyclops(int x, int y, Maze maze) {
        super(x, y, maze, AdversityType.ENEMY,5);
    }

    @Override

    public void triggerEffect(Player player) {
        System.out.println("❄️ Un Ice Cyclops genera Nebbia Gelida! La tua velocità è ridotta per " + getEffectDuration() + " secondi.");
        //player.setSpeed(player.getSpeed()/3);
        player.applySpeedEffect(getEffectDuration(),0.33);
    }
}
