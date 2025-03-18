package org.labyrinthShiftChat.model.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.labyrinthShiftChat.model.Maze;
import org.labyrinthShiftChat.model.Player;

@Entity
@DiscriminatorValue("FreezingFog")
public class FreezingFog extends Adversity {

    public FreezingFog() {
        super();
    }

    public FreezingFog(int x, int y, Maze maze) {
        super(x, y, maze, AdversityType.OBSTACLE,7);
    }

    @Override
    public void triggerEffect(Player player) {
        System.out.println("Hai attivato la Nebbia Gelida! La tua velocità è ridotta di un terzo per 7 secondi.");
        // Logica per rallentare il giocatore
        //player.setSpeed(player.getSpeed()/3);
        player.applySpeedEffect(getEffectDuration(), 0.33);
    }
}
