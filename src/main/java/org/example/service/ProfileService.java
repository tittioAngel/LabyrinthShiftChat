package org.example.service;

import lombok.NoArgsConstructor;
import org.example.dao.ProfileDAO;
import org.example.model.CompletedLevel;
import org.example.model.Profile;

import java.util.List;

@NoArgsConstructor
public class ProfileService {

    private static final ProfileDAO profileDAO = new ProfileDAO();

    public Profile profileLogin(final String username, final String password) {
        Profile profile = profileDAO.findByUsernameAndPassword(username, password);
        if (profile != null) {
            profile.setCompletedLevels(getCompletedLevelsByProfile(profile));
        }
        return profile;
    }

    public Profile createProfile(final String username,final String password) {
        Profile profile = new Profile(username, password);
        if (!profileDAO.save(profile)) {
            return null;
        }
        return profile;
    }

    public List<CompletedLevel> getCompletedLevelsByProfile(final Profile profile) {
        return profileDAO.findCompletedLevelsByProfile(profile);
    }


    public void updateProfile(Profile profile) {
        profileDAO.update(profile);
    }
}
