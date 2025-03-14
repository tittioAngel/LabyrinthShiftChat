package org.labirynthShiftChat.controller;

import lombok.NoArgsConstructor;
import org.labirynthShiftChat.model.Profile;
import org.labirynthShiftChat.service.ProfileService;
import org.labirynthShiftChat.singleton.GameSessionManager;
import org.labirynthShiftChat.view.LoginView;

import java.util.HashMap;

@NoArgsConstructor
public class LoginController {

    private final GameSessionManager gameSessionManager = GameSessionManager.getInstance();
    private final ProfileService profileService = new ProfileService();

    private final LoginView loginView = new LoginView();

    public HashMap<String, String> retrieveCredentials() {
        loginView.show();
        HashMap<String, String> credentials = new HashMap<>();
        credentials.put("username", loginView.readStringInput("👉 Inserisci username: "));
        credentials.put("password", loginView.readStringInput("👉 Inserisci password: "));
        return credentials;
    }

    public void manageUserLogin() {
        HashMap<String, String> credentials = retrieveCredentials();
        Profile profile = profileService.profileLogin(credentials.get("username"), credentials.get("password"));
        if (profile != null) {
            gameSessionManager.setProfile(profile);
        }
    }

    public void manageUserSignUp() {
        HashMap<String, String> credentials = retrieveCredentials();
        Profile profile = profileService.createProfile(credentials.get("username"), credentials.get("password"));
        if (profile != null) {
            gameSessionManager.setProfile(profile);
        }
    }

}
