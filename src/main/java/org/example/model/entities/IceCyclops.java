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
        super(x, y, maze, AdversityType.ENEMY,5);
    }

    @Override

    public void triggerEffect(Player player) {
        System.out.println("❄️ Un Ice Cyclops genera Nebbia Gelida! La tua velocità è ridotta per " + getEffectDuration() + " secondi.");
        player.setSpeed(player.getSpeed() / 3);
        try {
            Thread.sleep(getEffectDuration() * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        player.setSpeed(player.getSpeed() * 3); // Ripristina velocità normale
    }
}
