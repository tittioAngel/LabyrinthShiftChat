package org.example.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Enemy")
public class Enemy extends Tile {
    private String type;
    private int damage;

    public Enemy() {}

    public Enemy(String type, int damage, int x, int y, Maze maze) {
        super(x, y, false, maze); // ✅ I nemici NON sono attraversabili
        this.type = type;
        this.damage = damage;
    }

    public String getType() { return type; }
    public int getDamage() { return damage; }
}
