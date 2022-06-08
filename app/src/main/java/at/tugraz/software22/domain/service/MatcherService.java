package at.tugraz.software22.domain.service;

import java.util.ArrayList;
import java.util.List;

import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.repository.UserRepository;

public class MatcherService {
    List<String> interestingProfiles = new ArrayList<>();


    public MatcherService(UserRepository userService) {

    }

    public User getNextInterestingProfile() {

        return null;
    }
}
