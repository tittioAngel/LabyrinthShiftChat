package org.labirynthShiftChat.model.entities;

import jakarta.persistence.*;
import org.labirynthShiftChat.model.Maze;
import org.labirynthShiftChat.model.Player;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "adversity_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@NoArgsConstructor
public abstract class Adversity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int x;
    private int y;

    @ManyToOne
    @JoinColumn(name = "maze_id", nullable = false)
    private Maze maze;

    @Enumerated(EnumType.STRING)
    private AdversityType adversityType;

    private boolean activated = false;

    @Column(nullable = false)
    private int effectDuration = 0;


    public Adversity(int x, int y, Maze maze, AdversityType adversityType, int effectDuration) {
        this.x = x;
        this.y = y;
        this.maze = maze;
        this.adversityType = adversityType;
        this.effectDuration = effectDuration;
    }

    // Metodo finale che garantisce l'esecuzione dell'effetto una sola volta
    public final void applyEffect(Player player) {
        if (!activated) {
            triggerEffect(player);
            activated = true;
        }
    }

    /**
     * Metodo astratto che, in base al tipo di adversità, applica l'effetto sul giocatore.
     * Le sottoclassi implementeranno questo metodo per definire il comportamento specifico.
     */
    public abstract void triggerEffect(Player player);

}
