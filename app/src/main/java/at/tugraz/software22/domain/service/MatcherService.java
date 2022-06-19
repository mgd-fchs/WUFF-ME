package at.tugraz.software22.domain.service;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import at.tugraz.software22.Constants;
import at.tugraz.software22.domain.entity.User;
import at.tugraz.software22.domain.enums.UserType;
import at.tugraz.software22.domain.repository.UserRepository;

public class MatcherService {
    List<User> interestingProfiles = new ArrayList<>();
    private final FirebaseDatabase database;

    public MatcherService(FirebaseDatabase database) {
        this.database = database;
    }

    public void getNextInterestingProfile(MutableLiveData<User> userMutableLiveData, UserType ownType) {
        if (interestingProfiles.size() <= 1) {
            fetchNewProfiles(ownType,userMutableLiveData);
        }
        else {
            userMutableLiveData.postValue(interestingProfiles.remove(0));
        }
    }

    private void fetchNewProfiles(UserType ownType, MutableLiveData<User> userMutableLiveData) {
       database.getReference().child(Constants.USER_TABLE).get().addOnSuccessListener(dataSnapshot -> {
           for (DataSnapshot child : dataSnapshot.getChildren()) {
               User user = child.getValue(User.class);
               switch (ownType) {
                   case SEARCHER:
                       if (user.getType() == UserType.OWNER || user.getType() == UserType.BOTH) { // TODO filter already swiped
                           interestingProfiles.add(user);
                       }
                       break;
               }
           }
           if (!interestingProfiles.isEmpty()) {
               userMutableLiveData.postValue(interestingProfiles.remove(0));
           }
       });
    }
}
