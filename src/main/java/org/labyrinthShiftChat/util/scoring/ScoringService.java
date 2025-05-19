package org.labyrinthShiftChat.util.scoring;

import org.labyrinthShiftChat.model.DifficultyLevel;

import java.util.Map;

public class ScoringService {
    private final Map<DifficultyLevel, ScoringStrategy> strategyMap;

    public ScoringService() {
        strategyMap = Map.of(
                DifficultyLevel.EASY, new EasyScoringStrategy(),
                DifficultyLevel.MEDIUM, new MediumScoringStrategy(),
                DifficultyLevel.HARD, new HardScoringStrategy(),
                DifficultyLevel.EXTREME, new ExtremeScoringStrategy()
        );
    }

    public int computeStars(DifficultyLevel difficulty, long timeTakenSeconds) {
        return strategyMap.get(difficulty).computeStars(timeTakenSeconds);
    }
}

