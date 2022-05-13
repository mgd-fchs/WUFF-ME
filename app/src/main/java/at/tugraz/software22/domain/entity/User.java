package at.tugraz.software22.domain.entity;

import java.time.LocalDate;

public class User {
    private String username;
    private String password;
    private String email;

    private String job;
    private LocalDate birthday;

    public User (String username, LocalDate birthday, String job) {
        this.username = username;
        this.birthday = birthday;
        this.job = job;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
