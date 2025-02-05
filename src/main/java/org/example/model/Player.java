package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "players", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
@Getter
@Setter
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
}
