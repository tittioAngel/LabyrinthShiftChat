package org.labyrinthShiftChat.model.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.labyrinthShiftChat.model.Maze;
import org.labyrinthShiftChat.model.Player;

@Entity
@DiscriminatorValue("Slime")
public class Slime extends Adversity {

    public Slime() {
        super();
    }

    public Slime(int x, int y, Maze maze) {
        super(x, y, maze, AdversityType.OBSTACLE,2);
    }

    @Override
    public void triggerEffect(Player player) {
        System.out.println("Hai attivato lo Slime! Sei immobilizzato per 2 secondi.");
        try {
            Thread.sleep(getEffectDuration() * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
