package org.labyrinthShiftChat.util.scoring;

public class HardScoringStrategy implements ScoringStrategy {

    @Override
    public int computeStars(long timeTakenSeconds) {
        if (timeTakenSeconds <= 50) return 3;
        if (timeTakenSeconds <= 70) return 2;
        return 1;
    }
}
