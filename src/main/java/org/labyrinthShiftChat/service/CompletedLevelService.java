package org.labyrinthShiftChat.service;

import lombok.NoArgsConstructor;
import org.labyrinthShiftChat.dao.CompletedLevelDAO;
import org.labyrinthShiftChat.model.CompletedLevel;

@NoArgsConstructor
public class CompletedLevelService {

    private final CompletedLevelDAO completedLevelDao = new CompletedLevelDAO();

    public void save(CompletedLevel completedLevel) {
        completedLevelDao.save(completedLevel);
    }

    public CompletedLevel getLevelRetried(Long levelId, Long profileId) {
        return completedLevelDao.findByLevelAndProfile(levelId, profileId);
    }

    public void updateCompletedLevel(CompletedLevel completedLevel) {
        completedLevelDao.update(completedLevel);
    }
}
