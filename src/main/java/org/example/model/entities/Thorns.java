package org.example.model.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.example.model.Maze;
import org.example.model.Player;

@Entity
@DiscriminatorValue("Thorns")
public class Thorns extends Obstacle {
    public Thorns() {
        super();
    }

    public Thorns(int x, int y, Maze maze) {
        super(x, y, maze);
    }

    @Override
    public void applyEffect(Player player) {
        System.out.println("Hai attivato le Spine! La tua velocità è ridotta del 50% per 5 secondi.");
        // Inserire qui la logica per ridurre la velocità del giocatore.
    }
}
