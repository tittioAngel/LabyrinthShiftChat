package org.example.model;

public enum DifficultyLevel {
    EASY(5, 1, 1, 10),      // Labirinto 5x5, 1 nemico, 1 ostacolo, tempo 10 sec
    MEDIUM(7, 3, 2, 15),    // Labirinto 7x7, 3 nemici, 2 ostacoli, tempo 15 sec
    HARD(10, 5, 3, 20);     // Labirinto 10x10, 5 nemici, 3 ostacoli, tempo 20 sec

    private final int mazeSize;
    private final int enemyCount;
    private final int obstacleCount;
    private final int previewTime; // Tempo di previsualizzazione

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
