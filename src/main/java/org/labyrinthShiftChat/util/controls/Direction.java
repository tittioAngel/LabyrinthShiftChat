package org.labyrinthShiftChat.util.controls;

public enum Direction {

    UP, RIGHT, DOWN, LEFT;

    public static Direction convertInputToDirection(String input) {
        return switch (input.toUpperCase()) {
            case "W" -> Direction.UP;
            case "A" -> Direction.LEFT;
            case "S" -> Direction.DOWN;
            case "D" -> Direction.RIGHT;
            default -> null;
        };
    }
}
