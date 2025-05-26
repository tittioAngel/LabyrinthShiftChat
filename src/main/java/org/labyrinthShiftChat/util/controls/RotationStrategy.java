package org.labyrinthShiftChat.util.controls;

public interface RotationStrategy {

    Direction mapInput(Direction input);

    void rotateRandom();

    void resetRotation();
}
