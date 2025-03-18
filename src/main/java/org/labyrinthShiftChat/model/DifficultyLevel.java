package org.labyrinthShiftChat.model;

public enum DifficultyLevel {
    EASY(7, 1, 1, 5),      // Labirinto 7x7, 1 nemico, 1 ostacolo, tempo 10 sec
    MEDIUM(9, 3, 2, 10),    // Labirinto 9x9 3 nemici, 2 ostacoli, tempo 15 sec
    HARD(11, 3, 2, 15),     // Labirinto 11x11, 5 nemici, 3 ostacoli, tempo 20 sec
    EXTREME(13, 4, 3, 20);  // Labirinto 13x13, 10 nemici, 5 ostacoli, tempo 5 sec

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