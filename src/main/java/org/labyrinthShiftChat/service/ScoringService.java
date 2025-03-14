package org.labyrinthShiftChat.service;

public class ScoringService {
    /**
     * Calcola il punteggio in stelle in base al tempo impiegato per completare il mini-maze.
     *
     * @param timeTakenSeconds il tempo impiegato in secondi
     * @return 3 stelle se il tempo è ≤ 30 secondi,
     *         2 stelle se il tempo è compreso tra 31 e 40 secondi,
     *         1 stella se il tempo supera i 40 secondi.
     */
    public int computeStars(long timeTakenSeconds) {
        if (timeTakenSeconds <= 30) {
            return 3;
        } else if (timeTakenSeconds <= 40) {
            return 2;
        } else {
            return 1;
        }
    }
}
