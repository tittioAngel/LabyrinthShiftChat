package org.example.service;

import lombok.NoArgsConstructor;
import org.example.dao.CompletedLevelDao;
import org.example.model.CompletedLevel;

@NoArgsConstructor
public class CompletedLevelService {

    private final CompletedLevelDao completedLevelDao = new CompletedLevelDao();

    public void save(CompletedLevel completedLevel) {
        completedLevelDao.save(completedLevel);
    }
}
