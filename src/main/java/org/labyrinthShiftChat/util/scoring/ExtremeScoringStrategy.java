package org.labyrinthShiftChat.util.scoring;

public class ExtremeScoringStrategy implements ScoringStrategy {

    @Override
    public int computeStars(long timeTakenSeconds) {
        if (timeTakenSeconds <= 60) return 3;
        if (timeTakenSeconds <= 90) return 2;
        return 1;
    }
}
