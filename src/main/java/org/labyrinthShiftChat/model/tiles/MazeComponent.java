package org.labyrinthShiftChat.model.tiles;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.labyrinthShiftChat.model.Player;
import org.labyrinthShiftChat.model.Tile;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "mazeComponent_entityName", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class MazeComponent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "tile_id")
    private Tile tile;

    @Column(nullable = false)
    private boolean isWalkable;

    private boolean activated;

    public MazeComponent(Tile tile, boolean isWalkable) {
        this.tile = tile;
        this.isWalkable = isWalkable;
    }

    public final void applyEffect(Player player) {
        if (!activated) {
            triggerEffect(player);
            activated = true;
        }
    }

    public abstract void triggerEffect(Player player);

}
