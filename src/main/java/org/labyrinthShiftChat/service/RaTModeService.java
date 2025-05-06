package org.labyrinthShiftChat.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.labyrinthShiftChat.model.*;
import org.labyrinthShiftChat.singleton.GameSessionManager;

@AllArgsConstructor
public class RaTModeService {

    private final GameSessionManager gameSessionManager = GameSessionManager.getInstance();
    private final ProfileService profileService = new ProfileService();

    public void manageSaveCompletedRaT(DifficultyLevel difficultyLevel, int miniMazeCompleted) {
        Profile profile = gameSessionManager.getProfile();
        int previousRecord;

        switch (difficultyLevel) {
            case EASY:
                previousRecord = (profile.getRecordRatEasy() != null) ? profile.getRecordRatEasy() : 0;
                if (miniMazeCompleted > previousRecord) {
                    profile.setRecordRatEasy(miniMazeCompleted);
                }
                break;

            case MEDIUM:
                previousRecord = (profile.getRecordRatMedium() != null) ? profile.getRecordRatMedium() : 0;
                if (miniMazeCompleted > previousRecord) {
                    profile.setRecordRatMedium(miniMazeCompleted);
                }
                break;

            case HARD:
                previousRecord = (profile.getRecordRatHard() != null) ? profile.getRecordRatHard() : 0;
                if (miniMazeCompleted > previousRecord) {
                    profile.setRecordRatHard(miniMazeCompleted);
                }
                break;
        }

        profileService.updateProfile(profile);
    }

}
