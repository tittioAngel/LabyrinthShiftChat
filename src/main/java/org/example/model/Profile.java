package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "profile")
@Getter
@Setter
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompletedLevel> completedLevels;

    public Profile(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public void addCompletedLevel(final CompletedLevel completedLevel) {
        if (this.completedLevels == null) {
            this.completedLevels = new ArrayList<>();
        }
        this.completedLevels.add(completedLevel);
    }



    /**
     * Metodo per contare quanti livelli sono stati completati
     * @return int, se la lista di livelli completati è vuota torna 0
     */
    public int getCompletedLevelsCount() {
        if (this.completedLevels == null) {
            return 0;
        }
        return this.completedLevels.size();
    }
}
