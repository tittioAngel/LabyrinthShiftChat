package org.labyrinthShiftChat.model.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.labyrinthShiftChat.model.Maze;
import org.labyrinthShiftChat.model.Player;

@Entity
@DiscriminatorValue("Thorns")
public class Thorns extends Adversity {

    public Thorns() {
        super();
    }

    public Thorns(int x, int y, Maze maze) {
        super(x, y, maze, AdversityType.OBSTACLE,5);
    }

    @Override
    public void triggerEffect(Player player) {
        System.out.println("Hai attivato le Spine! La tua velocità è ridotta del 50% per 5 secondi.");
        //player.setSpeed(player.getSpeed()/2);
        player.applySpeedEffect(getEffectDuration(), 0.5);
    }
}
