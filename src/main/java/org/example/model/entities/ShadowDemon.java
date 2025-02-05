package org.example.model.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import org.example.model.Maze;
import org.example.model.Player;

@Entity
@DiscriminatorValue("ShadowDemon")
public class ShadowDemon extends Enemy {
    public ShadowDemon() {
        super();
    }

    public ShadowDemon(int x, int y, Maze maze) {
        super(x, y, maze);
    }

    @Override
    public void attack(Player player) {
        System.out.println("Uno Shadow Demon ti ha avvistato! Ti insegue per 7 secondi.");
        // Inserire qui la logica per attivare l'inseguimento per 7 secondi.
    }
}
