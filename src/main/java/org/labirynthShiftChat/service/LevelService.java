package org.labirynthShiftChat.service;

import lombok.NoArgsConstructor;
import org.labirynthShiftChat.dao.LevelDAO;
import org.labirynthShiftChat.model.Level;

@NoArgsConstructor
public class LevelService {

    static final LevelDAO levelDAO = new LevelDAO();

    public Level findLevelByNumber(int number) {
        return levelDAO.retrieveLevelByNumber(number);
    }

    public void playLevel(int levelNumber) {
        Level level = levelDAO.retrieveLevelByNumber(levelNumber);

    }
}
