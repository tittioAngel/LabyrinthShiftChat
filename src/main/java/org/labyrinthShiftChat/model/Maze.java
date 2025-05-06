package org.labyrinthShiftChat.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Maze")
@Getter
@Setter
@NoArgsConstructor
public class Maze {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "maze", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Tile> tiles;

    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficulty;

    public Maze(DifficultyLevel difficulty) {
        this.difficulty = difficulty;
        this.tiles = new ArrayList<>();
    }

    public void addTile(Tile tile) {
        tiles.add(tile);
    }
}
