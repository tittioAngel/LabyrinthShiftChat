package org.example.model.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.example.model.Maze;
import org.example.model.Player;

@Entity
@DiscriminatorValue("FreezingFog")
public class FreezingFog extends Adversity {

    public FreezingFog() {
        super();
    }

    public FreezingFog(int x, int y, Maze maze) {
        super(x, y, maze, AdversityType.OBSTACLE,7);
    }

    @Override
    public void triggerEffect(Player player) {
        System.out.println("Hai attivato la Nebbia Gelida! La tua velocità è ridotta di un terzo per 7 secondi e la visibilità è limitata.");
        // Logica per rallentare il giocatore e limitare la visibilità
    }
}
