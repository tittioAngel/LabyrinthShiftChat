package org.labyrinthShiftChat.foundation;

import org.hibernate.Session;
import org.labyrinthShiftChat.foundation.config.HibernateUtil;
import org.labyrinthShiftChat.model.Level;

public class LevelDAO extends GenericDAO<Level> {

    public LevelDAO() {
        super(Level.class);
    }

    public Level retrieveLevelByNumber(int levelNumber) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Level " +
                            "WHERE levelIdentifier = :levelNumber", Level.class)
                    .setParameter("levelNumber", levelNumber)
                    .uniqueResult();
        }
    }
}
