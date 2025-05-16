package org.labyrinthShiftChat.foundation;

import org.labyrinthShiftChat.model.CompletedLevel;
import org.hibernate.Session;

public class CompletedLevelDAO extends GenericDAO<CompletedLevel> {

    public CompletedLevelDAO() {
        super(CompletedLevel.class);
    }

    public CompletedLevel findByLevelAndProfile(Long levelID, Long profileID) {
        try (Session session = org.labyrinthShiftChat.foundation.config.HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM CompletedLevel " +
                            "WHERE level.id = :levelID " +
                            "AND profile.id = :profileID", CompletedLevel.class)
                    .setParameter("levelID", levelID)
                    .setParameter("profileID", profileID)
                    .uniqueResult();
        }
    }
}
