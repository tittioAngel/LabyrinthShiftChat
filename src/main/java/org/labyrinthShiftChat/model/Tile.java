package org.labyrinthShiftChat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Tile")
@Getter
@Setter
@NoArgsConstructor
public class Tile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int x;
    private int y;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maze_id", nullable = false)
    private Maze maze;

    public Tile(int x, int y, Maze maze) {
        this.x = x;
        this.y = y;
        this.maze = maze;
    }


}
