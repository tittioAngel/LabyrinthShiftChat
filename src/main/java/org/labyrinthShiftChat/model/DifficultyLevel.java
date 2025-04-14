package org.labyrinthShiftChat.model;

public enum DifficultyLevel {
    EASY(7, 1, 1, 7),
    MEDIUM(9, 3, 2, 10),
    HARD(11, 3, 3, 13),
    EXTREME(13, 4, 3, 16);

    private final int mazeSize;
    private final int enemyCount;
    private final int obstacleCount;
    private final int previewTime;

    DifficultyLevel(int mazeSize, int enemyCount, int obstacleCount, int previewTime) {
        this.mazeSize = mazeSize;
        this.enemyCount = enemyCount;
        this.obstacleCount = obstacleCount;
        this.previewTime = previewTime;
    }


    public static String formatDifficulyLevel(DifficultyLevel difficulty) {
        return difficulty.name().toLowerCase().replace('_', ' ').replaceFirst(".",
                String.valueOf(difficulty.name().charAt(0)).toUpperCase());
    }

    public int getMazeSize() {
        return mazeSize;
    }

    public int getEnemyCount() {
        return enemyCount;
    }

    public int getObstacleCount() {
        return obstacleCount;
    }

    public int getPreviewTime() {
        return previewTime;
    }
}