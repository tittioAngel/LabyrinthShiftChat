package org.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "players", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private int score;

    public Player() {} // Costruttore senza argomenti richiesto da Hibernate

    public Player(String username) {
        this.username = username;
        this.score = 0;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
