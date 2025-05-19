package org.labyrinthShiftChat.util.controls;

import java.util.Arrays;
import java.util.Random;

public class RandomRotationStrategy implements RotationStrategy {

    private final Direction[] directions = Direction.values();
    private int rotationIndex;
    private final Random random = new Random();

    public RandomRotationStrategy() {
        rotateRandom();
    }

    @Override
    public void rotateRandom() {
        rotationIndex = random.nextInt(directions.length);
    }

    @Override
    public Direction mapInput(Direction input) {
        int index = Arrays.asList(directions).indexOf(input);
        return directions[(index + rotationIndex) % 4];
    }

    @Override
    public void resetRotation() {
        rotationIndex = 0;
    }
}
