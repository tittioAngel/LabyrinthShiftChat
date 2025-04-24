package org.labyrinthShiftChat.util;

public class GameTimer {
    private final long totalMillis;
    private final long startTime;
    private long pausedDuration;
    private long pauseStart;
    private boolean isPaused;

    public GameTimer(long totalSeconds) {
        this.totalMillis = totalSeconds * 1000L;
        this.startTime = System.currentTimeMillis();
        this.pausedDuration = 0;
        this.isPaused = false;
    }

    public void pause() {
        if (!isPaused) {
            pauseStart = System.currentTimeMillis();
            isPaused = true;
        }
    }

    public void resume() {
        if (isPaused) {
            pausedDuration += System.currentTimeMillis() - pauseStart;
            isPaused = false;
        }
    }

    public long getElapsedTime() {
        if (isPaused) {
            return pauseStart - startTime - pausedDuration;
        }
        return System.currentTimeMillis() - startTime - pausedDuration;
    }

    public long getRemainingTime() {
        return totalMillis - getElapsedTime();
    }

    public boolean isTimeOver() {
        return getRemainingTime() <= 0;
    }
}
