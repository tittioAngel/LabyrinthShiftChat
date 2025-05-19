package org.labyrinthShiftChat.util.controls;

public class NoRotationStrategy implements RotationStrategy {

    @Override
    public Direction mapInput(Direction input) {
        return input;
    }

    @Override
    public void rotateRandom() {

    }

    @Override
    public void resetRotation() {

    }
}