package org.labyrinthShiftChat.util.scoring;

public class MediumScoringStrategy implements ScoringStrategy {

    @Override
    public int computeStars(long timeTakenSeconds) {
        if (timeTakenSeconds <= 40) return 3;
        if (timeTakenSeconds <= 55) return 2;
        return 1;
    }
}
