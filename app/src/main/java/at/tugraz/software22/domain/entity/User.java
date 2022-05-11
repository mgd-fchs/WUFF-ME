package at.tugraz.software22.domain.entity;

public class User {
    private String username;
    private String password;
    private String email;

    public User (String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) { this.username = username; }
}
