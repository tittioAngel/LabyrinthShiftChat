package org.example.model.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.example.model.Maze;
import org.example.model.Player;

@Entity
@DiscriminatorValue("PhantomHorse")
public class PhantomHorse extends Enemy {
    public PhantomHorse() {
        super();
    }

    public PhantomHorse(int x, int y, Maze maze) {
        super(x, y, maze);
    }

    @Override
    public void attack(Player player) {
        System.out.println("Un Phantom Horse ti ha colpito! Verrai spinto indietro di 4 tile.");
        // Inserire qui la logica per spostare il giocatore indietro di 4 tile.
    }
}
