package org.labyrinthShiftChat.util.scoring;

public class EasyScoringStrategy implements ScoringStrategy {

    @Override
    public int computeStars(long timeTakenSeconds) {
        if (timeTakenSeconds <= 30) return 3;
        if (timeTakenSeconds <= 40) return 2;
        return 1;
    }
}
