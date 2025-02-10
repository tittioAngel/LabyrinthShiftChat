package org.example.singleton;

import lombok.Getter;
import lombok.Setter;
import org.example.model.Profile;

@Getter
@Setter
public class SessionFactory {

    private static final SessionFactory instance = new SessionFactory();

    private Profile profile;

    private SessionFactory() {
        this.profile = null;
    }

    public static SessionFactory getInstance() {
        return instance;
    }

    public boolean isLoggedIn() {
        return profile != null;
    }

    public void logOut() {
        profile = null;
    }
}
