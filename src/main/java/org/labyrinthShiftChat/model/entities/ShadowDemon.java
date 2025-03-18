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
        super(x, y, maze, AdversityType.ENEMY,0);
    }

    @Override
    public void triggerEffect(Player player) {
        System.out.println("👹 Uno Shadow Demon ti ha colpito! Verrai Portato alla posizione di partenza");
        player.setPosition(0,0);
    }
}
