package org.labyrinthShiftChat.util.controls;

import lombok.Getter;

@Getter
public class RotatingControls {

    public enum Direction {
        UP, RIGHT, DOWN, LEFT
    }

    private RotationStrategy strategy;

    public RotatingControls(RotationStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(RotationStrategy strategy) {
        this.strategy = strategy;
    }

    public Direction mapInput(Direction input) {
        return strategy.mapInput(input);
    }

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
