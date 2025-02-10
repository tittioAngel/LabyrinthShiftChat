package org.example.service;

import lombok.NoArgsConstructor;
import org.example.dao.ProfileDAO;
import org.example.model.CompletedLevel;
import org.example.model.Profile;

import java.util.List;

@NoArgsConstructor
public class ProfileService {

    private static final ProfileDAO profileDAO = new ProfileDAO();

    private static final GameService gameService = new GameService();

    public Profile profileLogin(final String username, final String password) {
        final Profile profile = getProfileByUsernameAndPassword(username, password);

        if (profile == null) {
            gameService.selectStartMenuOption(false);
        }
        return profile;
    }

    public Profile createProfile(final String username,final String password) {
        Profile profile = new Profile(username, password);
        if (!profileDAO.save(profile)) {
            gameService.selectStartMenuOption(true);
        }
        return profile;
    }

    public Profile getProfileByUsernameAndPassword(final String username, final String password) {
        return profileDAO.findByUsernameAndPassword(username, password);
    }

    public List<CompletedLevel> getCompletedLevelsByProfile(final Profile profile) {
        return profileDAO.findCompletedLevelsByProfile(profile);
    }
}
