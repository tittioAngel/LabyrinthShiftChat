package org.labyrinthShiftChat.model;

public enum GameMode {
    STORY_MODE;

    public static String formatGameMode(GameMode mode) {
        return mode.name().toLowerCase().replace('_', ' ').replaceFirst(".",
                String.valueOf(mode.name().charAt(0)).toUpperCase());
    }
}
