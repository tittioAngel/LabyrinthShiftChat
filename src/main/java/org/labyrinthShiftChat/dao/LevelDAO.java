package org.labyrinthShiftChat.dao;

import lombok.NoArgsConstructor;
import org.labyrinthShiftChat.config.HibernateUtil;
import org.labyrinthShiftChat.model.Level;
import org.hibernate.Session;

@NoArgsConstructor
public class LevelDAO {

    public Level retrieveLevelByNumber(int levelNumber) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Level " +
                            "WHERE levelIdentifier = :levelNumber ", Level.class)
                    .setParameter("levelNumber", levelNumber)
                    .uniqueResult();
        }
    }
}
