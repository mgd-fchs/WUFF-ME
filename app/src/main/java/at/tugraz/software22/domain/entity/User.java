package at.tugraz.software22.domain.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import at.tugraz.software22.domain.enums.UserType;

public class User {
    private String username;
    private UserType type = UserType.SEARCHER;
    private String job;
    private LocalDate birthday;
    private List<String> picturePaths = new ArrayList<>();

    public User() {
    }



    public User(String username) {
        this.username = username;
    }

    public List<String> getPicturePaths() {
        return picturePaths;
    }

    public void setPicturePaths(List<String> picturePaths) {
        this.picturePaths = picturePaths;
    }

    public void addPicturePath(String path) {
        this.picturePaths.add(path);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
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
