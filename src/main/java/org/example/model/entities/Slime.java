package org.example.model.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.example.model.Maze;
import org.example.model.Player;

@Entity
@DiscriminatorValue("Slime")
public class Slime extends Obstacle {
    public Slime() {
        super();
    }

    public Slime(int x, int y, Maze maze) {
        super(x, y, maze);
    }

    @Override
    public void applyEffect(Player player) {
        System.out.println("Hai attivato lo Slime! Sei immobilizzato per 2 secondi.");
        // Inserire qui la logica per immobilizzare il giocatore.
    }
}
