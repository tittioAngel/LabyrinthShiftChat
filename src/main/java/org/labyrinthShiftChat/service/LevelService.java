package org.labyrinthShiftChat.service;

import lombok.NoArgsConstructor;
import org.labyrinthShiftChat.dao.LevelDAO;
import org.labyrinthShiftChat.model.Level;

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
