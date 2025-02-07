package org.example.model.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.example.model.Maze;
import org.example.model.Player;

@Entity
@DiscriminatorValue("PhantomHorse")
public class PhantomHorse extends Adversity {

    public PhantomHorse() {
        super();
    }

    public PhantomHorse(int x, int y, Maze maze) {
        super(x, y, maze, AdversityType.ENEMY);
    }

    @Override
    public void triggerEffect(Player player) {
        System.out.println("Un Phantom Horse ti ha colpito! Verrai spinto indietro di 4 tile.");
        // Logica per spostare il giocatore indietro di 4 tile
    }
}
