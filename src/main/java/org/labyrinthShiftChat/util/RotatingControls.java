package org.labyrinthShiftChat.util;

import lombok.Getter;

import java.util.Arrays;
import java.util.Random;

@Getter
public class RotatingControls {

    public enum Direction {
        UP, RIGHT, DOWN, LEFT
    }

    private final Direction[] directions = {Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT};

    private int rotationIndex = 0;
    private final Random random = new Random();

    public void rotateRandom() {
        rotationIndex = random.nextInt(4);
    }

    public Direction mapInput(Direction input) {
        int inputIndex = Arrays.asList(directions).indexOf(input);
        int mappedIndex = (inputIndex + rotationIndex) % 4;
        return directions[mappedIndex];
    }

    public void resetRotation() {
        rotationIndex = 0;
    }

    public static RotatingControls.Direction convertInputToDirection(String input) {
        return switch (input.toUpperCase()) {
            case "W" -> RotatingControls.Direction.UP;
            case "A" -> RotatingControls.Direction.LEFT;
            case "S" -> RotatingControls.Direction.DOWN;
            case "D" -> RotatingControls.Direction.RIGHT;
            default -> null;
        };
    }
}