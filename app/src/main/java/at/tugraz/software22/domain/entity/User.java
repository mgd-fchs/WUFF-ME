package at.tugraz.software22.domain.entity;

import java.time.LocalDate;

public class User {
    private String username;
    private String password;
    private String email;
    private LocalDate birthday;

    public User (String username, LocalDate birthday) {
        this.username = username;
        this.birthday = birthday;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) { this.username = username; }

    public LocalDate getBirthday() {
        return birthday;
    }
}
