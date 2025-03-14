package org.labirynthShiftChat.model.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.labirynthShiftChat.model.Maze;
import org.labirynthShiftChat.model.Player;

@Entity
@DiscriminatorValue("PhantomHorse")
public class PhantomHorse extends Adversity {

    public PhantomHorse() {
        super();
    }

    public PhantomHorse(int x, int y, Maze maze) {
        super(x, y, maze, AdversityType.ENEMY,0);
    }

    @Override
    public void triggerEffect(Player player) {
        System.out.println("Un Phantom Horse ti ha colpito! Verrai spinto indietro di 4 tile.");
        int[] previousPos = player.getPreviousPosition(4);
        player.setPosition(previousPos[0], previousPos[1]);
    }
}
