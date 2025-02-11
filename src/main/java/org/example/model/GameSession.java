package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "game_sessions")
@Getter
@Setter
@NoArgsConstructor
public class GameSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @ManyToOne
    @JoinColumn(name = "maze_id", nullable = false)
    private Maze maze;

    @ManyToOne
    @JoinColumn(name = "current_tile_id")
    private Tile currentTile; // ✅ Posizione del giocatore nel minimaze

    private int timeRemaining;

    public GameSession(Profile profile, Maze maze, Tile startTile, int timeRemaining) {
        this.profile = profile;
        this.maze = maze;
        this.currentTile = startTile;
        this.timeRemaining = timeRemaining;
    }

}
