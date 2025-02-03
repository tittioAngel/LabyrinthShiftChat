package org.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "game_sessions")
public class GameSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @ManyToOne
    @JoinColumn(name = "maze_id", nullable = false)
    private Maze maze;

    @ManyToOne
    @JoinColumn(name = "current_tile_id")
    private Tile currentTile; // ✅ Posizione del giocatore nel minimaze

    private int timeRemaining;

    public GameSession() {}

    public GameSession(Player player, Maze maze, Tile startTile, int timeRemaining) {
        this.player = player;
        this.maze = maze;
        this.currentTile = startTile;
        this.timeRemaining = timeRemaining;
    }

    public Long getId() { return id; }
    public Player getPlayer() { return player; }
    public Maze getMaze() { return maze; }
    public Tile getCurrentTile() { return currentTile; }
    public int getTimeRemaining() { return timeRemaining; }

    public void setCurrentTile(Tile currentTile) { this.currentTile = currentTile; }
    public void setTimeRemaining(int timeRemaining) { this.timeRemaining = timeRemaining; }
}
