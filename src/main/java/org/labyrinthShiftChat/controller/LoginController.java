package org.labyrinthShiftChat.controller;

import lombok.NoArgsConstructor;
import org.labyrinthShiftChat.model.Profile;
import org.labyrinthShiftChat.service.ProfileService;
import org.labyrinthShiftChat.singleton.GameSessionManager;
import org.labyrinthShiftChat.view.LoginView;

import java.util.HashMap;

@NoArgsConstructor
public class LoginController {

    private final GameSessionManager gameSessionManager = GameSessionManager.getInstance();
    private final ProfileService profileService = new ProfileService();

    private final LoginView loginView = new LoginView();

    public HashMap<String, String> retrieveCredentials() {
        loginView.print("\n📜 Inserisci le credenziali per accedere/registrarti 📜");
        HashMap<String, String> credentials = new HashMap<>();
        credentials.put("username", loginView.readStringInput("👉 Inserisci username: "));
        credentials.put("password", loginView.readStringInput("👉 Inserisci password: "));
        return credentials;
    }

    public Profile manageUserLogin() {
        HashMap<String, String> credentials = retrieveCredentials();
        Profile profile = profileService.profileLogin(credentials.get("username"), credentials.get("password"));
        if (profile != null) {
            gameSessionManager.setProfile(profile);
        }
        return profile;
    }

    public Profile manageUserSignUp() {
        HashMap<String, String> credentials = retrieveCredentials();
        Profile profile = profileService.createProfile(credentials.get("username"), credentials.get("password"));
        if (profile != null) {
            gameSessionManager.setProfile(profile);
        }
        return profile;
    }

}
