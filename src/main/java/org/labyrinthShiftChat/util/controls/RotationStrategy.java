package org.labyrinthShiftChat.util.controls;

import org.labyrinthShiftChat.util.controls.RotatingControls.Direction;

public interface RotationStrategy {

    Direction mapInput(Direction input);

    void rotateRandom();

    void resetRotation();
}
