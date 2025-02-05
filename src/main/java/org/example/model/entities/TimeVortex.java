package org.example.model.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.example.model.Maze;
import org.example.model.Player;

@Entity
@DiscriminatorValue("TimeVortex")
public class TimeVortex extends Obstacle {
    public TimeVortex() {
        super();
    }

    public TimeVortex(int x, int y, Maze maze) {
        super(x, y, maze);
    }

    @Override
    public void applyEffect(Player player) {
        System.out.println("Hai attivato il Vortice Temporale! Verrai spostato indietro di 2 tile.");
        // Inserire qui la logica per spostare il giocatore indietro di 2 tile.
    }
}
