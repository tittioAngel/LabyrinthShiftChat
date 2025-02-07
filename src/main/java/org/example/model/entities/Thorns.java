package org.example.model.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.example.model.Maze;
import org.example.model.Player;

@Entity
@DiscriminatorValue("Thorns")
public class Thorns extends Adversity {

    public Thorns() {
        super();
    }

    public Thorns(int x, int y, Maze maze) {
        super(x, y, maze, AdversityType.OBSTACLE);
    }

    @Override
    public void triggerEffect(Player player) {
        System.out.println("Hai attivato le Spine! La tua velocità è ridotta del 50% per 5 secondi.");
        // Logica per ridurre la velocità del giocatore
    }
}
