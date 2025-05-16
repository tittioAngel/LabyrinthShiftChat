package org.labyrinthShiftChat.service;

import org.labyrinthShiftChat.model.*;
import org.labyrinthShiftChat.singleton.GameSessionManager;


public class StoryModeService {
    private final GameSessionManager gameSessionManager = GameSessionManager.getInstance();
    private final CompletedLevelService completedLevelService = new CompletedLevelService();
    private final ProfileService profileService = new ProfileService();

    public void manageSaveCompletedLevel(int averageStars) {
        CompletedLevel completedLevelRetried = completedLevelService.getLevelRetried(gameSessionManager.getLevelSelected().getId(), gameSessionManager.getProfile().getId());

        if (completedLevelRetried != null) {
            if (completedLevelRetried.getScore() < averageStars) {
                completedLevelRetried.setScore(averageStars);
                completedLevelService.updateCompletedLevel(completedLevelRetried);
                gameSessionManager.setProfile(profileService.refreshProfile(gameSessionManager.getProfile()));
            }
        } else {
            CompletedLevel completedLevel = new CompletedLevel();
            completedLevel.setLevel(gameSessionManager.getLevelSelected());
            completedLevel.setScore(averageStars);
            completedLevel.setProfile(gameSessionManager.getProfile());

            completedLevelService.save(completedLevel);
            gameSessionManager.getProfile().addCompletedLevel(completedLevel);
            profileService.updateProfile(gameSessionManager.getProfile());
        }
    }

}
