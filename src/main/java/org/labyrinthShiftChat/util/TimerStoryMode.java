package org.labyrinthShiftChat.util;

public class TimerStoryMode {

    private final long startTime;
    private long timeLimitMillis;
    private final double playerSpeed;

    public TimerStoryMode(long timeLimitMillis, double playerSpeed) {
        this.startTime = System.currentTimeMillis();
        this.timeLimitMillis = timeLimitMillis;
        this.playerSpeed = playerSpeed;
    }

    public long getElapsedTimeMillis() {
        if (playerSpeed != 1.0) {
            return (long) ((System.currentTimeMillis() - startTime) * (1 / playerSpeed));
        }
        return System.currentTimeMillis() - startTime;
    }

    public long getRemainingTimeSeconds() {
        long elapsed = getElapsedTimeMillis();
        return Math.max((timeLimitMillis - elapsed) / 1000, 0);
    }

    public boolean isTimeOver() {
        return getElapsedTimeMillis() >= timeLimitMillis;
    }

    public long getTotalTimeSeconds() {
        return getElapsedTimeMillis() / 1000;
    }

    public void addTimeSeconds(int secondsToAdd) {
        if (secondsToAdd > 0) {
            this.timeLimitMillis += secondsToAdd * 1000L;
        }
    }
}
