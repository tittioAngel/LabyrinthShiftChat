package org.labyrinthShiftChat.model.tiles.enemy;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import org.labyrinthShiftChat.model.Player;
import org.labyrinthShiftChat.model.Tile;
import org.labyrinthShiftChat.model.tiles.MazeComponent;

@Entity
@DiscriminatorValue("ShadowDemon")
@NoArgsConstructor
public class ShadowDemon extends MazeComponent {

    public ShadowDemon(Tile tile) {
        super(tile, true);
    }

    @Override
    public void triggerEffect(Player player) {
        System.out.println("\n💀 Uno Shadow Demon ti ha colpito! Verrai Portato alla posizione di partenza");
        player.setPosition(0,0);
    }
}
