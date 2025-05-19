package org.labyrinthShiftChat.model;

import lombok.Getter;

@Getter
public enum DifficultyLevel {
    EASY("EASY", 7, 50, 1, 1, 1, 7, 60, 20),
    MEDIUM("MEDIUM", 9, 65, 3, 2, 2, 10, 80, 30),
    HARD("HARD", 11, 85, 3, 3, 3, 13, 100, 45),
    EXTREME("EXTREME", 13, 110,4, 3, 4, 16, 120, 65);

    private final String difficultyName;
    private final int mazeSize;
    private final int maxTime;
    private final int enemyCount;
    private final int obstacleCount;
    private final int powerUpCount;
    private final int previewTime;
    private final int ratTime;
    private final int ratMovements;

    DifficultyLevel(String difficultyName, int mazeSize, int maxTime, int enemyCount, int obstacleCount, int powerUpCount, int previewTime, int ratTime, int ratMovements) {
        this.difficultyName = difficultyName;
        this.mazeSize = mazeSize;
        this.maxTime = maxTime;
        this.enemyCount = enemyCount;
        this.obstacleCount = obstacleCount;
        this.powerUpCount = powerUpCount;
        this.previewTime = previewTime;
        this.ratTime = ratTime;
        this.ratMovements = ratMovements;
    }


    public static String formatDifficultyLevel(DifficultyLevel difficulty) {
        return difficulty.name().toLowerCase().replace('_', ' ').replaceFirst(".",
                String.valueOf(difficulty.name().charAt(0)).toUpperCase());
    }
}