package org.example.model.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.example.model.Maze;
import org.example.model.Player;

@Entity
@DiscriminatorValue("FreezingFog")
public class FreezingFog extends Obstacle {
    public FreezingFog() {
        super();
    }

    public FreezingFog(int x, int y, Maze maze) {
        super(x, y, maze);
    }

    @Override
    public void applyEffect(Player player) {
        System.out.println("Hai attivato la Nebbia Gelida! La tua velocità è ridotta di un terzo per 7 secondi e la visibilità è limitata.");
        // Inserire qui la logica per rallentare il giocatore e ridurre la visibilità.
    }
}
