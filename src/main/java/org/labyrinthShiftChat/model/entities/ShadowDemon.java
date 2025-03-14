package org.labyrinthShiftChat.model.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.labyrinthShiftChat.model.Maze;
import org.labyrinthShiftChat.model.Player;

@Entity
@DiscriminatorValue("ShadowDemon")
public class ShadowDemon extends Adversity {

    public ShadowDemon() {
        super();
    }

    public ShadowDemon(int x, int y, Maze maze) {
        super(x, y, maze, AdversityType.ENEMY,7);
    }

    @Override
    public void triggerEffect(Player player) {
        System.out.println("👹 Uno Shadow Demon ti ha avvistato! Ti insegue per " + getEffectDuration() + " secondi.");
        for (int i = 0; i < getEffectDuration(); i++) {
//            int[] previousPos = player.getPreviousPosition(1);
//            player.setPosition(previousPos[0] + 1, previousPos[1]); // Si avvicina al giocatore
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
