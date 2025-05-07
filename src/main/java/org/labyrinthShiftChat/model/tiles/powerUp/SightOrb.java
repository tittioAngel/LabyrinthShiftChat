package org.labyrinthShiftChat.model.tiles.powerUp;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.labyrinthShiftChat.model.Player;
import org.labyrinthShiftChat.model.Tile;
import org.labyrinthShiftChat.model.tiles.MazeComponent;

@Entity
@DiscriminatorValue("SightOrb")
@Getter
@NoArgsConstructor
public class SightOrb extends MazeComponent {

    public SightOrb(Tile tile) {
        super(tile, true);
    }

    @Override
    public void triggerEffect(Player player) {
        System.out.println("\n✨ Hai raccolto la Sfera della Visione! Ecco a te nuovamente la visione totale del MiniMaze!");
        player.setShowAllMaze(true);
    }
}
