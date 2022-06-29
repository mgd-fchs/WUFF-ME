package at.tugraz.software22.domain.entity;

import com.google.firebase.database.Exclude;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import at.tugraz.software22.domain.enums.UserType;

public class User {
    private String username;
    private UserType type = UserType.NON;
    private String job;
    private LocalDate birthday;
    private long birthDayInEpoch;
    private List<String> picturePaths = new ArrayList<>();
    private String id;
    private List<String> leftSwipedProfiles = new ArrayList<>();
    private List<String> rightSwipedProfiles = new ArrayList<>();

    public User() {
    }

    public void addLeftSwipedProfile(String id) {
        leftSwipedProfiles.add(id);
    }
    public void addRightSwipedProfile(String id) {
        rightSwipedProfiles.add(id);
    }


    public List<String> getLeftSwipedProfiles() {
        return leftSwipedProfiles;
    }

    public void setLeftSwipedProfiles(List<String> leftSwipedProfiles) {
        this.leftSwipedProfiles = leftSwipedProfiles;
    }

    public List<String> getRightSwipedProfiles() {
        return rightSwipedProfiles;
    }

    public void setRightSwipedProfiles(List<String> rightSwipedProfiles) {
        this.rightSwipedProfiles = rightSwipedProfiles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User(String username) {
        this.username = username;
    }

    public List<String> getPicturePaths() {
        return picturePaths;
    }

    public void getProfilePicture(){}

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

    @Exclude
    public LocalDate getBirthday() {
        return birthday;
    }

    @Exclude
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
        this.birthDayInEpoch = birthday.toEpochDay();
    }

    public long getBirthDayInEpoch() {
        return birthDayInEpoch;
    }

    public void setBirthDayInEpoch(long birthDayInEpoch) {
        this.birthDayInEpoch = birthDayInEpoch;
        this.birthday = LocalDate.ofEpochDay(birthDayInEpoch);
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
