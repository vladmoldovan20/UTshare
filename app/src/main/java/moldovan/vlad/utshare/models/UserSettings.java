package moldovan.vlad.utshare.models;

/**
 * Created by vladu on 12/18/2017.
 */

public class UserSettings {

    private User user;
    private UserAccountSettings settings;

    public UserSettings(User user, UserAccountSettings settings) {
        this.user = user;
        this.settings = settings;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserAccountSettings getSettings() {
        return settings;
    }

    public void setSettings(UserAccountSettings settings) {
        this.settings = settings;
    }

    public UserSettings(){

    }

    @Override
    public String toString() {
        return "UserSettings{" +
                "user=" + user +
                ", settings=" + settings +
                '}';
    }
}


