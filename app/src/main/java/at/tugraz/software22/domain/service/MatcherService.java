package at.tugraz.software22.domain.service;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import at.tugraz.software22.Constants;
import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.enums.UserType;

public class MatcherService {
    List<User> interestingProfiles = new ArrayList<>();
    private final FirebaseDatabase database;

    public MatcherService(FirebaseDatabase database) {
        this.database = database;
    }

    public void getNextInterestingProfile(MutableLiveData<User> userMutableLiveData, User currentUser) {
        if (interestingProfiles.size() <= 1) {
            fetchNewProfiles(currentUser, userMutableLiveData);
        } else {
            userMutableLiveData.postValue(interestingProfiles.remove(0));
        }
    }

    private void fetchNewProfiles(User currentUser, MutableLiveData<User> userMutableLiveData) {
        database.getReference().child(Constants.USER_TABLE).get().addOnSuccessListener(dataSnapshot -> {
            for (DataSnapshot child : dataSnapshot.getChildren()) {
                User user = child.getValue(User.class);
                if (!currentUser.getLeftSwipedProfiles().contains(user.getId())
                        && !currentUser.getRightSwipedProfiles().contains(user.getId())) {

                    switch (currentUser.getType()) {
                        case SEARCHER:
                            if ((user.getType() == UserType.OWNER || user.getType() == UserType.BOTH)) {
                                interestingProfiles.add(user);
                            }
                            break;
                        case OWNER:
                            if ((user.getType() == UserType.SEARCHER || user.getType() == UserType.BOTH)) {
                                interestingProfiles.add(user);
                            }
                            break;
                    }
                }
            }
            if (!interestingProfiles.isEmpty()) {
                userMutableLiveData.postValue(interestingProfiles.remove(0));
            }
        });
    }
}
