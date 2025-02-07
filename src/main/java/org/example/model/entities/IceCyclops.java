package org.example.model.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.example.model.Maze;
import org.example.model.Player;

@Entity
@DiscriminatorValue("IceCyclops")
public class IceCyclops extends Adversity {

    public IceCyclops() {
        super();
    }

    public IceCyclops(int x, int y, Maze maze) {
        super(x, y, maze, AdversityType.ENEMY);
    }

    @Override
    public void triggerEffect(Player player) {
        System.out.println("Un Ice Cyclops genera Nebbia Gelida sulle caselle adiacenti, rallentando i tuoi movimenti.");
        // Logica per applicare l'effetto simile a FreezingFog sulle caselle adiacenti
    }
}
