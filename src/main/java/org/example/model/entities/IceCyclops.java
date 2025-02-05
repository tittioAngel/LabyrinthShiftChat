package org.example.model.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.example.model.Maze;
import org.example.model.Player;

@Entity
@DiscriminatorValue("IceCyclops")
public class IceCyclops extends Enemy {
    public IceCyclops() {
        super();
    }

    public IceCyclops(int x, int y, Maze maze) {
        super(x, y, maze);
    }

    @Override
    public void attack(Player player) {
        System.out.println("Un Ice Cyclops genera Nebbia Gelida sulle caselle adiacenti, rallentando i tuoi movimenti.");
        // Inserire qui la logica per applicare l'effetto di Freezing Fog sulle caselle adiacenti.
    }
}
