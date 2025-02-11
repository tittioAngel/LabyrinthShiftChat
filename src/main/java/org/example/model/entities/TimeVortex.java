package org.example.model.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.example.model.Maze;
import org.example.model.Player;

@Entity
@DiscriminatorValue("TimeVortex")
public class TimeVortex extends Adversity {

    public TimeVortex() {
        super();
    }

    public TimeVortex(int x, int y, Maze maze) {
        super(x, y, maze, AdversityType.OBSTACLE,0);
    }

    @Override
    public void triggerEffect(Player player) {
        System.out.println("Hai attivato il Vortice Temporale! Verrai spostato indietro di 2 tile.");
        int[] previousPos = player.getPreviousPosition(2);
        player.setPosition(previousPos[0], previousPos[1]);
    }

}
